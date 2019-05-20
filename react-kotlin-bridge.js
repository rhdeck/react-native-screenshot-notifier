import { NativeModules } from "react-native";
//#region Code for object react_native_screenshot_notifier
const Nativereact_native_screenshot_notifier =
  NativeModules.react_native_screenshot_notifier;
const test = async s => {
  return await Nativereact_native_screenshot_notifier.test(s);
};
//#endregion
//#region Exports
export { test };
//#endregion
