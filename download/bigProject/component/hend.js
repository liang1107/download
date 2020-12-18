/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import Video from 'react-native-video';
import { View, Text, StyleSheet, Dimensions ,Image ,DeviceEventEmitter,TouchableOpacity, TouchableHighlight,PermissionsAndroid ,NativeModules,StatusBar,AsyncStorage} from 'react-native';
import Swiper from 'react-native-swiper';
import RNFS from 'react-native-fs';
import {_downloadFile} from "./../util/DloadFile";
import zero from './../util/zero'
const ExternalDirectoryPath = RNFS.ExternalDirectoryPath;

console.log(ExternalDirectoryPath,"文件路径")
//设备宽高
const screenWidth = Math.round(Dimensions.get('window').width);
const screenHeight = Math.round(Dimensions.get('window').height);
//设计宽高1870,1925
const pwidth=1081,pheight=1925;
var that;
class Hend extends Component {
    // 初始化函数(变量是可以改变的,充当状态机的角色)https://www.runoob.com/try/demo_source/mov_bbb.mp4 http://www.bestwond.com/static/test04.mp4
    constructor(props) {
        super(props);
        this.state = {
          
          
        }
        that=this
    }
    loadStart(){
        
    }
    setDuration(){

    }
    setTime(){

    }
    onEnd(){

    }
    videoError(){

    }
    componentWillMount= async()=>{
      
          var data = await AsyncStorage.getItem('ad');
         
      
    }
    componentDidMount() {
        // android.wondware.json.s.send.action
        this.deEmitter = DeviceEventEmitter.addListener('JsonData',async (msg)=>{
            console.log(msg,"获取得到的信息")
           
            
        })
    }
    componentWillUnmount() {
        this.deEmitter.remove();
        
       
    }
    render() {
        
        return (
            <View style={styles.main}>
              
                <Swiper
               
                 autoplay={true}
                 autoplayTimeout={5}
                 showsPagination={false} //页面下原点
               
                horizontal={true} //true 横排 false 纵派
                loop={true}
                index={0}    //
                showsButtons={false}  //显示控制按钮
                >
                   
                
                         
                      
                               <Image  resizeMode='cover' source={require('./../res/main/ad2.png')} style={{width: '100%',flex:1}} />
                              
                             <Image  resizeMode='cover' source={require('./../res/main/ad3.png')} style={{width: '100%',flex:1}} />
                              
                         
                           
                       
                     
                </Swiper>
               
             
                
            </View>
           

        );
    }
    
 
    
  
    

}
const styles = StyleSheet.create({
    main:{
        flex: 1,
    },
    backgroundVideo:{
        flex: 1,
    }
    
  
});

export default Hend;
