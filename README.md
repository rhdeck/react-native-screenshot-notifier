# react-native-screenshot-notifier
React-Native module for getting notified of screenshots on Android. (note this does not disable )

# Installation
```bash
yarn add react-native-screenshot-notifier
react-native link react-native-screenshot-notifier
```
# usage: Hooks!
## useScreenshotNotifier()
Returns array of `id`, `fileName` and `path` of captured screenshot
### Usage
```js
const { id, fileName, path} = useScreenshotNotifier()
```
### Sample App
```js
import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { useScreenshotNotifier } from "react-native-screenshot-notifier";
export default () => {
  const { id, path, fileName } = useScreenshotNotifier();
  return (
    <View style={styles.container}>
      <Text style={styles.welcome}>Welcome to React Native!</Text>
      {id ? (
        <Text>{`Screenshot Taken! ${id}: ${path}/${fileName}`}</Text>
      ) : (
        <Text>NO screenshot taken... yet!</Text>
      )}
    </View>
  );
};
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#F5FCFF"
  },
  welcome: {
    fontSize: 20,
    textAlign: "center",
    margin: 10
  }
});
```
## useDisableScreenshots(disableScreenshots = true)
Disables screenshots (or turns them back on if `disableScreenshots` is `false`)
### Usage
```js
useDisableScreenshots(); // That's it. 
```
### Sample App
```js
import React from "react";
import { StyleSheet, Text, View } from "react-native";
import { useDisableScreenshots } from "react-native-screenshot-notifier";
export default () => {
  useDisableScreenshots();
  return (
    <View style={styles.container}>
      <Text style={styles.welcome}>I dare you to screenshot me</Text>
    </View>
  );
};
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#F5FCFF"
  },
  welcome: {
    fontSize: 20,
    textAlign: "center",
    margin: 10
  }
});
```
