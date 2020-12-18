/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from 'react';
import { View, Text, StyleSheet, Dimensions ,Image ,DeviceEventEmitter,TouchableOpacity, StatusBar,Button ,NativeModules,ImageBackground,Alert} from 'react-native';
import zero from './../../util/zero'
import Toast from './../../util/Toast'
import {EasyLoading,Loading} from "./../../util/EasyLoading.js"
import Hend from "./../../component/hend"

//设备宽高
const screenWidth = Math.round(Dimensions.get('window').width);
const screenHeight = Math.round(Dimensions.get('window').height);
//设计宽高1870,1925
const pwidth=1081,pheight=1925;
var that;var s = 0;
class Main extends Component {
    // 初始化函数(变量是可以改变的,充当状态机的角色)
    constructor(props) {
        super(props);
        this.state = {
            
        }
        that=this
    }
    
    componentWillMount(){
       
              
       
    }
    componentWillUnmount() {
        // this.deEmitter.remove();
       
    }
    componentDidMount(){
        
    }
   
    render() {
        
        return (
           
          
             <View  style={styles.main}>
                   <Loading />
                   <TouchableOpacity  style={styles.mainconticom} onPress={()=>{this.pickup()}}>
                    <Text>下载通信组件</Text>
                   </TouchableOpacity>
                   <TouchableOpacity style={styles.mainconticom} onPress={()=>{this.sendup()}}>
                    <Text>下载大屏app</Text>
                   </TouchableOpacity>
                   <TouchableOpacity style={styles.mainconticom} onPress={()=>{this.sendup2()}}>
                    <Text>删除文件</Text>
                   </TouchableOpacity>
                   <TouchableOpacity style={styles.mainconticom} onPress={()=>{this.sendup3()}}>
                    <Text>产生崩溃</Text>
                   </TouchableOpacity>
               
                
                
                
               
             </View>
                   
               
              
               

           

        );
    }
    getcell(it,ite){
        return <View style={{justifyContent:"center",alignItems:"center"}}>
                <Text style={{color:"#ffe096",fontSize:zero.yFont(38)}}>{it}</Text>
                 <Text style={{color:"#fff",fontSize:zero.yFont(34)}}>{ite}</Text>
        </View>
    }
    
    pickup(){
        console.log("下载通信组件")
        // alert("取件")
        // this.props.navigation.navigate('PickUp')
        NativeModules.OpenNativeModule.show('开始下载通信组件',5)
        var url="http://www.bestwond.com/static/tongxun.apk"
        // var url="http://gassad.mq.bestwond.com/gassad/yilinbao_app/yilinbao_app.apk"
        NativeModules.DownloadApk.downloading(url, 'zujian.apk');
    }
    sendup(){
        console.log("下载大屏app")
    //    NativeModules.OpenNativeModule.show('该功能正在拼命派送。。。',5)
    //     Alert.alert("该功能正在拼命派送。。。")
        // alert("寄件")SendUp 
        // var string ="{'qr':'ww.baidu.con','opty':'1123'}"
        // NativeModules.OpenNativeModule.isNetworkConnected((a)=>{
        //     console.log(a,"是否有网络")
        // })
        // NativeModules.OpenNativeModule.getData("device_id","device_id")
        NativeModules.OpenNativeModule.show('开始下载大屏app',5)
        var url="http://file.mq.bestwond.com/api/public/dl/zz1rm1Si/zckx.apk"
        // var url="http://gassad.mq.bestwond.com/gassad/yilinbao_app/yilinbao_app.apk"
        NativeModules.DownloadApk.downloading(url, 'bigapp.apk');
        //  this.props.navigation.navigate('SendUpWeb')
    }
    sendup2(){
        console.log("删除文件")
        NativeModules.DownloadApk.fileDelete();
    }
    sendup3(){
        for (i = 0; i < 100; i++) { 
            alert(a)
         }
       
    }
   
    

}
const styles = StyleSheet.create({
    main:{
        backgroundColor:'blue',
        flex: 1,
       
    },
    maintop:{
        height:zero.yHeight(467),
        marginTop:zero.yHeight(28)
    },
    maincont:{
        marginLeft:zero.yWidth(45),marginRight:zero.yWidth(45),
        flexDirection:"row",
        justifyContent:"space-between",
        alignItems:"center",
        marginTop:zero.yHeight(63),

    },
    maincont2:{
        marginLeft:zero.yWidth(45),marginRight:zero.yWidth(45),
        flexDirection:"row",
        justifyContent:"space-between",
        alignItems:"center",
        marginTop:zero.yHeight(26),

    },
    mainconticom:{
        marginTop:zero.yHeight(52),
        height:zero.yHeight(209),
        width:zero.yWidth(481),
        backgroundColor:"rgba(225,225,225,0.2)",
        borderRadius:5,
        alignItems:"center",
        justifyContent:"center",
        flexDirection:'row',
        backgroundColor:'red'
    },
    buttoms:{
        width:zero.yWidth(236),
        height:zero.yHeight(60),
       borderRadius:zero.yHeight(30),
       alignItems:"center",
       justifyContent:"center",
    //    marginTop:zero.yHeight(35)
    marginLeft:zero.yWidth(75)
    },
    mainfoot:{
        marginLeft:zero.yWidth(45),marginRight:zero.yWidth(45),
        height:zero.yHeight(186),
        backgroundColor:"rgba(225,225,225,0.2)",
        borderRadius:5,
        marginTop:zero.yHeight(24)
    },
    cell:{
        flexDirection:"row",marginLeft:zero.yHeight(30),marginRight:zero.yHeight(30),
        justifyContent:"space-between"
    }
    
  
});

export default Main;
