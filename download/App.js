/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format定义路由
 * @flow
 */

// import React, { Component } from 'react';
// import {StackNavigator} from 'react-navigation';

// import Main from './bigProject/page/main/Main';                                 //首页
// import Hend from "./bigProject/component/hend"

// const App = StackNavigator({
//         Home: {screen: Main},
     
//     }
//     ,{
//         initialRouteName: 'Home', // 默认显示界面
//         headerMode: 'none',
       
//     }
// );
// export default App;

// AppRegistry.registerComponent('gewdDemo01', () => gewdDnpm emo01);

import React, { Component } from 'react';
import {createStackNavigator} from 'react-navigation';

import { View, Text, StyleSheet, NativeModules ,Image ,BackHandler,TouchableOpacity, StatusBar,PermissionsAndroid ,Toast,ImageBackground,AsyncStorage} from 'react-native';
import zero from "./bigProject/util/zero"
import Main from './bigProject/page/main/Main';                                 //首页
import Hend from "./bigProject/component/hend";                 //头部
import Footer from "./bigProject/component/foot"               //底部

var  nativeModule =  NativeModules.OpenNativeModule
const MyStack = createStackNavigator({
    Home: {screen: Main},
   
},
     {
        initialRouteName: 'Home',
        headerMode:"none"
      }
 );
 var click = 0;
class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            time :''
        }
        
    }
  static router = {
    ...MyStack.router,
    getStateForAction: (action, lastState) => {
      // check for custom actions and return a different navigation state.
      return MyStack.router.getStateForAction(action, lastState);
    }
  };
  componentWillMount(){
    // nativeModule.getSeverMsg((msg)=>{ 
    //     console.log(msg,"设备信息")
    //     alert(msg)
    //   });
    StatusBar.setBarStyle("light-content", true);
    StatusBar.setBackgroundColor("transparent")
    StatusBar.setTranslucent(true);
    //显示网络指示器
    StatusBar.setNetworkActivityIndicatorVisible(true);

    //隐藏的动画模式  'fade'： | 'slide'：
    StatusBar.showHideTransition = 'fade';
    StatusBar.setHidden(false, true);
    setInterval(()=>{
      this.setState({time:(new Date).getTime()+1000})
    },1000)
    
  }
  componentDidUpdate(lastProps) {
    // Navigation state has changed from lastProps.navigation.state to this.props.navigation.state
  }
  increaseSize(){
    //退出app
    BackHandler.exitApp();
      // nativeModule.changebar((IT)=>{
      //   console.log(IT,"更改成功")
      // },(e)=>{
      //   console.log(e,"状态栏出现问题")
      // });
  }
  fiveClick(){
    console.log(click)
    if(click==0){
      setTimeout(()=>{
        click=0
      },1000)
    }
    if(click>=6){
      click=0
      
      BackHandler.exitApp();
    //    nativeModule.changebar((IT)=>{ 
    //   console.log(IT,"更改成功")
    // },(e)=>{
    //   console.log(e,"状态栏出现问题")
    // });
     
      
    }else{
      click=click+1
    }
    
  }
  render() {
    const { navigation } = this.props;

    return (
        <>
       
     
      <MyStack  navigation={navigation} />
     
      </>
    );
  }


}
const styles = StyleSheet.create({
  hender:{
      marginTop:zero.yHeight(10),
      height:zero.yHeight(63),
      flexDirection:"row",
      justifyContent:'space-between',
      alignItems:"center",
      
  },
  version:{
    color:"#bdbdbd",
    fontSize:zero.yFont(15),
    position:"absolute",
    top:0,
    left:zero.yWidth(30)
  }
    
  
});

export default App;