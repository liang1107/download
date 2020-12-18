
import React, { Component } from 'react';
import { View, Text, StyleSheet ,Image ,TextInput,TouchableOpacity, BackHandler,PermissionsAndroid ,NativeModules,DeviceEventEmitter,AsyncStorage} from 'react-native';
import zero from './../util/zero'
import Toast from './../util/Toast'
import {EasyLoading,Loading} from "./../util/EasyLoading.js"


var that;
class Footer extends Component {
    // 初始化函数(变量是可以改变的,充当状态机的角色)
    constructor(props) {
        super(props);
        this.state = {
            url:"",
            phone:"",
            add:"",
        }
        that=this
    }
    // data:image/png;base64,
     componentWillMount = async()=>{
        try {
          var data = JSON.parse(await AsyncStorage.getItem('data'));
            if(data){
                zero.setBoxUrl(data.qr)
                NativeModules.OpenNativeModule.stringtopic(data.qr,(img)=>{
                    // console.log(img);
                    // alert(img)
                    // that.setState({
                    //     url:"data:image/png;base64,"+img,
                    //     phone:data.phone,
                    //     add:data.addr
                    // })
                })
               
            }
          } catch (error) {
            // Error saving data
          }
          this.deEmitter2 = DeviceEventEmitter.addListener('Sdata',(msg)=>{
           
        })
      
    }
     componentDidMount() {
        // Toast.add("号箱门已打开，取出物品后请关闭箱门");
        // android.wondware.json.s.send.action
        this.deEmitter = DeviceEventEmitter.addListener('JsonData',async (msg)=>{
            console.log(msg)
            var data =  JSON.parse(msg);
            if(data.opty=="info"){
                try {
                    await AsyncStorage.setItem('data', msg);
                    zero.setBoxUrl(data.qr)
                  } catch (error) {
                    // Error saving data
                  }
              
                //   NativeModules.OpenNativeModule.stringtopic(data.qr,(img)=>{
                //     // console.log(img);
                //     // alert(img)
                //         that.setState({
                //             url:"data:image/png;base64,"+img,
                //             phone:data.phone,
                //             add:data.addr
                //         })
                //     })
                //    that.setVersion()
               
            }else{
                // alert(msg);
            }
            
        })
    
    }
    setVersion(){
        NativeModules.OpenNativeModule.getSeverMsg((msg)=>{ 
           
          var DISPLAY =  that.getQueryVariable(msg,"DISPLAY")
          var MODEL = that.getQueryVariable(msg,"MODEL")
          var appVie =zero.getV()
          var strVersion="app_version="+zero.getV()+",DISPLAY="+DISPLAY+",MODEL="+MODEL+",app_type=app_big";
          var remark="首页返回关闭声音"
            NativeModules.OpenNativeModule.getDeviceId((device_id)=>{
                try{
                    console.log(strVersion,"strVersion")
                    console.log(device_id,"device_id")
                    var url ="api/app/device/version?device_id="+device_id;
                    zero.xhrVER(url,"GET").then((res)=>{
                        console.log(res,"设备获取版本号")
                        if(res.code==200){
                            if(res.data.length==0){
                                console.log("没有版本信息")
                                // 查看有没次版本信息
                                var canurl ="api/app/version?app_version="+strVersion+"&app_type=app_big"
                                zero.xhrVER(canurl,"GET").then((res)=>{
                                    console.log(res,"查看")
                                    if(res.code==200){
                                       if(res.data.length==0){
                                             // 添加版本信息
                                                var url2 = "api/app/version?app_type=app_big&app_version="+strVersion+"&remark="+remark+"&new_url=url"
                                                zero.xhrVER(url2,"POST").then((res)=>{
                                                    console.log(res,"添加版本")
                                                    if(res.code==200){
                                                        var url3="api/app/device/version?device_id="+device_id+"&version_id="+res.data.id
                                                        zero.xhrVER(url3,"PUT").then((res)=>{
                                                            console.log(res,"绑定信息")
                                                            if(res.code==200){
                                                                console.log("添加成功")
                                                            }else{
                                                                console.log("添加版本信息失败")
                                                            }
                        
                                                        }).catch((err)=>{console.log(err,"绑定信息")})
                                                    }else{
                                                        console.log("添加版本信息失败")
                                                    }

                                                }).catch((err)=>{console.log(err,"添加版本信息")})
                                       }else{
                                           //有这个版本，就进行绑定
                                           var datalistit =res.data[0]
                                            var url3="api/app/device/version?device_id="+device_id+"&version_id="+datalistit.id
                                            zero.xhrVER(url3,"PUT").then((res)=>{
                                                console.log(res,"绑定信息")
                                                if(res.code==200){
                                                    console.log("添加成功")
                                                }else{
                                                    console.log("添加版本信息失败")
                                                }
            
                                            }).catch((err)=>{console.log(err,"绑定信息")})
                                       }
                                    }else{
                                        console.log("查看版本信息失败")
                                    }

                                }).catch((err)=>{console.log(err,"添加版本信息")})
                               
                            }else{
                                console.log("有版本信息")
                                var datalist = res.data[0];
                                if(datalist.app_version==strVersion){
                                    //版本一致
                                    console.log("版本一致")
                                }else{
                                     //版本不致
                                    // 添加版本信息
                                    var canurl ="api/app/version?app_version="+strVersion+"&app_type=app_big"
                                    zero.xhrVER(canurl,"GET").then((res)=>{
                                        console.log(res,"查看")
                                        if(res.code==200){
                                           if(res.data.length==0){
                                                 // 添加版本信息
                                                    var url2 = "api/app/version?app_type=app_big&app_version="+strVersion+"&remark="+remark+"&new_url=url"
                                                    zero.xhrVER(url2,"POST").then((res)=>{
                                                        console.log(res,"添加版本")
                                                        if(res.code==200){
                                                            var url3="api/app/device/version?device_id="+device_id+"&version_id="+res.data.id
                                                            zero.xhrVER(url3,"PUT").then((res)=>{
                                                                console.log(res,"绑定信息")
                                                                if(res.code==200){
                                                                    console.log("添加成功")
                                                                }else{
                                                                    console.log("添加版本信息失败")
                                                                }
                            
                                                            }).catch((err)=>{console.log(err,"绑定信息")})
                                                        }else{
                                                            console.log("添加版本信息失败")
                                                        }
    
                                                    }).catch((err)=>{console.log(err,"添加版本信息")})
                                           }else{
                                               //有这个版本，就进行绑定
                                               var datalistit =res.data[0]
                                                var url3="api/app/device/version?device_id="+device_id+"&version_id="+datalistit.id
                                                zero.xhrVER(url3,"PUT").then((res)=>{
                                                    console.log(res,"绑定信息")
                                                    if(res.code==200){
                                                        console.log("添加成功")
                                                    }else{
                                                        console.log("添加版本信息失败")
                                                    }
                
                                                }).catch((err)=>{console.log(err,"绑定信息")})
                                           }
                                        }else{
                                            console.log("查看版本信息失败")
                                        }
    
                                    }).catch((err)=>{console.log(err,"添加版本信息")})
                                }
                            }
                        }
                    }).catch((err)=>{
                        console.log(err)
                    })
                }catch(error){
                    console.log(error)
                }
                console.log(appVie,"版本号")
                    // alert(strVersion)
            })
         
          });
    }
    getQueryVariable(query,variable){
            var query = query;
            var vars = query.split(",");
            for (var i=0;i<vars.length;i++) {
                var pair = vars[i].split("=");
                if(pair[0] == variable){return pair[1];}
            }
            return(false);
        }
    componentWillUnmount() {
        this.deEmitter?this.deEmitter.remove():"";
        this.deEmitter2? this.deEmitter2.remove():"";
    }
    getSeverId(){
        NativeModules.OpenNativeModule.getData("device_id","device_id")
    }
    chongqi(){
        console.log('重启')
        //重启
        // NativeModules.OpenNativeModule.reboot()
    }
    
    render() {
        
        return (
            <View style={styles.main}>
                 <Toast ></Toast>
              <Text>底部</Text>
               
               
               
              
               

            </View>

        );
    }
    
 
    
  
    

}
const styles = StyleSheet.create({
    main:{
        flex: 1,
        justifyContent:'center',
        flexDirection:"row",
        justifyContent:"space-between",marginLeft:zero.yWidth(45),marginRight:zero.yWidth(45),
        marginTop:zero.yHeight(41)
       
    },
    footright:{
        flex: 1,
        height:zero.yHeight(228),
        flexDirection:"column-reverse",
        alignItems:"flex-end"
    },
    foottop:{
        flexDirection:"row",
        // justifyContent:"center"
        alignItems:"center"
    }
    
  
});

export default Footer;
