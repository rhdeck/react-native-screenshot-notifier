import { NativeModules } from "react-native";
//#region Code for object react_native_screenshot_notifier
const Nativereact_native_screenshot_notifier =
  NativeModules.react_native_screenshot_notifier;
const test = async s => {
  return await Nativereact_native_screenshot_notifier.test(s);
};
const start = async () => {
  return await Nativereact_native_screenshot_notifier.start();
};
const resume = async () => {
  return await Nativereact_native_screenshot_notifier.resume();
};
const pause = async () => {
  return await Nativereact_native_screenshot_notifier.pause();
};
//#endregion
//#region Exports
export { test, start, resume, pause };
//#endregion
