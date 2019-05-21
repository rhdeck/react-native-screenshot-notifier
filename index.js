import {
  start as doStart,
  resume as doResume,
  pause as doPause,
  enableScreenshots,
  disableScreenshots
} from "./react-kotlin-bridge";
import { useEffect, useState } from "react";
import { DeviceEventEmitter, PermissionsAndroid, AppState } from "react-native";
//#region Android permission management
const getPermission = async (options = {}) => {
  const {
    title = "Screenshot Protector",
    message = "HIGHLY RECOMMENDED. Permission to detect when a screenshot is taken while this app is open.",
    buttonNeutral = "Ask me later",
    buttonNegative = "Cancel",
    buttonPositive = "OK"
  } = options;
  const granted = await PermissionsAndroid.request(
    PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE,
    { title, message, buttonNeutral, buttonNegative, buttonPositive }
  );
  if (granted === PermissionsAndroid.RESULTS.GRANTED) {
    //we can resume
  } else {
    throw "Screenshot permission denied";
  }
};
//#endregion
//#region Event Listening
let _listener = null;
let _listeners = [];
const onScreenshotTaken = async args => {
  _listeners.map(f => f(args));
};
//#endregion
//#region AppState management
let _appState = AppState.currentState;
const onAppStateChange = nextAppState => {
  if (_appState.match(/inactive|background/) && nextAppState === "active") {
    resume();
  } else {
    pause();
  }
  _appState = nextAppState;
};
//#endregion
//#region screenshot detection
const start = async options => {
  let callback;
  if (typeof options === "function") {
    callback = options;
    options = {};
  } else if (typeof options === "object") {
    callback = options.callback;
    delete options.callback;
  } else {
    //Flush when not a valid shape
    options = {};
    callback = null;
  }
  await getPermission(options);
  if (callback) addListener(callback);
  await doStart();
  AppState.addEventListener("change", onAppStateChange);
  await resume();
  return true;
};
const stop = async () => {
  await pause();
  AppState.removeListener("change", onAppStateChange);
};
const resume = async () => {
  if (!_listener)
    _listener = DeviceEventEmitter.addListener(
      "screenshotTaken",
      onScreenshotTaken
    );
  return doResume();
};
const pause = async () => {
  if (_listener) {
    DeviceEventEmitter.removeListener("screenshotTaken", onScreenshotTaken);
    _listener = null;
  }
  return doPause();
};
const addListener = f => {
  if (!_listeners.includes(f)) _listeners.push(f);
  return true;
};
const removeListener = f => {
  const i = _listeners.indexOf(f);
  if (i > -1) _listeners.splice(i, 1);
  return true;
};
//#endregion
//#region Hooks
const useScreenshotNotifier = options => {
  const [id, setId] = useState("");
  const [fileName, setFileName] = useState("");
  const [path, setPath] = useState("");
  useEffect(() => {
    start({
      ...options,
      callback: ({ id, fileName, path }) => {
        setId(id);
        setFileName(fileName);
        setPath(path);
      }
    });
    return () => stop();
  }, [options]);
  return { id, fileName, path };
};
const useDisableScreenshots = (isDisabled = true) => {
  useEffect(() => {
    isDisabled ? disableScreenshots() : enableScreenshots;
    return () => enableScreenshots();
  }, [isDisabled]);
};
//#endregion
export {
  start,
  stop,
  resume,
  pause,
  addListener,
  removeListener,
  useScreenshotNotifier,
  enableScreenshots,
  disableScreenshots,
  useDisableScreenshots
};
