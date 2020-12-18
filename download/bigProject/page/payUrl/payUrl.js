
import React, { Component } from 'react';
import { View, Text, StyleSheet ,Image ,ImageBackground,TouchableOpacity, BackHandler,PermissionsAndroid ,NativeModules,DeviceEventEmitter,AsyncStorage} from 'react-native';
import zero from './../../util/zero'
import {EasyLoading,Loading} from "./../../util/EasyLoading.js"
import { StackActions,NavigationActions } from 'react-navigation'


var that; var clearTime ="";
class PayUrl extends Component {
    // 初始化函数(变量是可以改变的,充当状态机的角色)
    constructor(props) {
        super(props);
        this.state = {
            url:"",
            time:60,
            ComeFrom:""
        }
        that=this
    }
    // data:image/png;base64,
     componentWillMount (){
        if(this.props.navigation.state.params && this.props.navigation.state.params.url){
          
            this.setState({
                url:this.props.navigation.state.params.url})
            if(this.props.navigation.state.params.ComeFrom){
                console.log(this.props.navigation.state.params.ComeFrom,"ComeFrom")
                this.setState({
                    ComeFrom:this.props.navigation.state.params.ComeFrom
                })
            }
          
       }else{
           console.log(this.props.navigation.state.params,"支付页面")
       }
    //    this.deEmitter2 = DeviceEventEmitter.addListener('Sdata',(msg)=>{
    //         var data =  JSON.parse(msg);
    //         console.log(data,"SdataMain")
    //         if(data.func_type=="2L"){
    //             //投递成功后第一次返回
    //             //取
    //             if(data['2L'].indexOf("G") != -1){
    //                 if(data['2L'].indexOf("+") != -1){
    //                     zero.setOpenMack(false)
    //                     EasyLoading.dismiss()
    //                 var a = data["2L"].split("+")[1].split("G")[0];
    //                 console.log("箱门编号")
    //                 console.log(parseInt(a,16))
    //                 const resetAction = StackActions.reset({
    //                     index: 0,
    //                     actions: [
    //                         NavigationActions.navigate({ routeName: 'Home'}),  
    //                     ]
    //                 })
    //                 this.props.navigation.dispatch(resetAction);
                   
                   
    //                 }else{
                        

    //                     console.log(data,"其他处理","空单开箱")
    //                     // alert("其他处理")
    //                 }
    //                 //存
    //             }else if(data['2L'].indexOf("S") != -1){
    //                 console.log(msg)
                
    //             }
            
                
    //         }else if(data.func_type=="lock_status_change"){
    //             //投递成功后第二次返回
    //             console.log(data,"返回的数据")
    //         }else if(data.func_type=="connect_status"){
    //             console.log(data,"网络切换");
    //         }else{
    //             // alert(JSON.stringify(data))
    //             NativeModules.OpenNativeModule.show(JSON.stringify(data),5)
    //         }
    //     })
    }
     componentDidMount() {
       that.addTime();
       
    }
    addTime(){
        clearTime && clearTimeout(clearTime);
        clearTime=setInterval(() => {
            var stopTime=that.state.time-1
            // console.log(stopTime)
            if(stopTime==0){
               
                that.setState({time:stopTime})
                that.clearin()
                that.goBack();
              
            }else{
                that.setState({time:stopTime})
            }
           
        }, 1000);
       
    }
    //重置时间
    resetTime(){
        that.setState({time:60})
    }
    //清除定时器
    clearin(){
        clearInterval(clearTime)
    }
    componentWillUnmount() {
        // this.deEmitter2.remove();
       
    }
    getSeverId(){
       
    }
    
    render() {
        
        return (
            <ImageBackground source={require('./../../res/main/zjbj.png')} style={{width: '100%',flex:1}}> 
                 <View style={styles.mainTop}>
                        <TouchableOpacity style={{flexDirection:"row",alignItems:"center"}} onPress={()=>{this.goBack()}}>
                            <Image resizeMode="center" source={require('./../../res/main/back.png')} style={{}}></Image>
                            <Text style={{fontSize:zero.yFont(36),color:"#fff", marginLeft:zero.yHeight(20)}}>返回</Text>
                        </TouchableOpacity>
                        <View style={{flexDirection:"row",alignItems:"center",marginRight:zero.yWidth(36)}}>
                            {/* <Text style={styles.buttonTop}>再次开箱</Text> */}
                            <Text style={{fontSize:zero.yFont(36),color:"#fff"}}>{this.state.time}S</Text>
                        </View>
                    </View>
             <View  style={styles.main}>
                 <Text style={{fontSize:zero.yFont(52),color:"#fff",marginBottom:zero.yHeight(66)}}>微信扫描二维码进行支付</Text>
            
             <Image source={this.state.url?{uri:this.state.url}:require('./../../res/main/ad.png')} style={{width: zero.yHeight(400),height:zero.yHeight(400)}}>

</Image>
                
              {
                  this.state.ComeFrom=="LANJIAN"?
                  <View style={{flexDirection:"row",marginTop:zero.yHeight(60)}}>
                      <TouchableOpacity style={[styles.buttoms,{backgroundColor:"#1ac18b"}]} onPress={()=>{this.goOnLanJian()}}>
                          <Text style={{color:"#fff",fontSize:zero.yFont(25)}}>继续揽件</Text>
                      </TouchableOpacity>
                      <TouchableOpacity  style={[styles.buttoms,{backgroundColor:"#f45d4a",marginLeft:zero.yWidth(42)}]}  onPress={()=>{this.goBack()}}>
                          <Text style={{color:"#fff",fontSize:zero.yFont(25)}}>返回首页</Text>
                      </TouchableOpacity>
                  </View>
                  :<View style={{flexDirection:"row",marginTop:zero.yHeight(60)}}>
                  <TouchableOpacity style={[styles.buttoms,{backgroundColor:"#1ac18b"}]} onPress={()=>{this.goOnQuJian()}}>
                      <Text style={{color:"#fff",fontSize:zero.yFont(25)}}>继续取件</Text>
                  </TouchableOpacity>
                  <TouchableOpacity  style={[styles.buttoms,{backgroundColor:"#f45d4a",marginLeft:zero.yWidth(42)}]}  onPress={()=>{this.goBack()}}>
                      <Text style={{color:"#fff",fontSize:zero.yFont(25)}}>返回首页</Text>
                  </TouchableOpacity>
              </View>
              }
                
               
             </View>
                   
               
              
               

            </ImageBackground>
        );
    }
    goBack(){
       that.clearin()
        const resetAction = StackActions.reset({
            index: 0,
            actions: [
                NavigationActions.navigate({ routeName: 'Home'}),  
            ]
        })
        this.props.navigation.dispatch(resetAction);
        
    }
    goOnLanJian(){
        that.clearin()
        that.props.navigation.navigate('LanJian',{zaicu:true});
    }
    goOnQuJian(){
        that.clearin()
        that.props.navigation.navigate('PickUp',{zaicu:true});
    }
 
    
  
    

}
const styles = StyleSheet.create({
    main:{
        // backgroundColor:'yellow',
        flex: 1,
        alignItems:"center",
        justifyContent:"center"
       
    },
    mainTop:{
        height:zero.yHeight(64),
        flexDirection:"row",
        justifyContent:'space-between',
        alignItems:"center",
        marginTop:zero.yHeight(42),
        marginLeft:zero.yHeight(42)
    },
    buttoms:{
        width:zero.yWidth(240),
        height:zero.yHeight(80),
        justifyContent:'center',
        alignItems:'center',
        
    }
  
});

export default PayUrl ;
