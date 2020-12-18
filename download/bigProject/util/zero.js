'use strict';
import {Dimensions, PixelRatio, Platform, Alert,NativeModules} from 'react-native';
import Sound from 'react-native-sound';
//测试
// const BASE_URL = 'http://gaas.easy.echosite.cn/openserviceApp/';
//线上接口
const BASE_URL = 'http://gaas.bestwond.com/openserviceApp/';
// const BASE_URL = "http://192.168.0.111/openServiceApp/";
//测试揽件
// const BASE_URLLAN = "http://gaas.easy.echosite.cn/mail/"
const BASE_URLLAN = "http://mail.sms.bestwond.com/mail/"
//版本更新
const versionURL ="http://gassadupload.mq.bestwond.com/"
//箱子的URL
var boxUrl="";
//登陆标示
var token = "";
//版本号
var version = 'v1.1.1-20201202';
//超时 当前网络不稳定，请联系管理员。
var outTime= 15000
//开箱返回标志
var openMack = true;
//是否播放声音
var isSound = true;
var whoosh;
var uiWidth = 1080;
var uiHeight = 1920;
var pixel = 1 / PixelRatio.get();
var screenWidth = Dimensions.get('window').width;
var screenHeight = Dimensions.get('window').height;
var pixelRatio = PixelRatio.get();
var fontScale = PixelRatio.getFontScale();
var scale = Math.min(
  Dimensions.get('window').height / uiHeight,
  Dimensions.get('window').width / uiWidth,
);

var utils = {
  /*宽度适配，例如我的设计稿某个样式宽度是50pt，那么使用就是：utils.yWidth(50)*/
  yWidth(value) {
    let getValue = (Dimensions.get('window').width * value) / uiWidth;
    if (getValue <= 1 && getValue > 0) {
      getValue = 1;
    }
    return getValue;
  },
  /*高度适配，例如我的设计稿某个样式高度是50pt，那么使用就是：utils.yHeight(50)*/
  yHeight(value) {
    let getValue = (Dimensions.get('window').height * value) / uiHeight;
    if (getValue <= 1 && getValue > 0) {
      getValue = 1;
    }
    return getValue;
  },
  /*字体大小适配，例如我的设计稿字体大小是17pt，那么使用就是：utils.yFont(17)*/
  yFont(value) {
    if (Platform.OS === 'android') {
      value = Math.round(((value * scale + 0.5) * pixelRatio) / fontScale);
      return value / pixelRatio;
    } else {
      let deviceWidth = screenWidth;
      let deviceHeight = screenHeight;
      let deviceRatio = pixelRatio;
      let fontSize = value;
      // console.log('deviceScreen_'+deviceRatio+'_'+deviceWidth+'_'+deviceHeight);
      if (deviceRatio === 2) {
        // iphone 5s and older Androids
        if (deviceWidth < 360) {
          return fontSize * 0.95;
        }
        // iphone 5
        if (deviceHeight < 667) {
          return fontSize;
        }
        // iphone 6-6s
        if (deviceHeight <= 735) {
          return fontSize * 1.15;
        }
        // older phablets
        return fontSize * 1.25;
      }
      if (deviceRatio === 3) {
        // catch larger devices
        // ie iphone 6s plus / 7 plus / mi note 等等 原1.27
        //x
        if (deviceHeight == 812) {
          return fontSize * 1.2;
        } else {
          //p
          return fontSize * 1.21;
        }
      }
    }
  },
  /**
   *  method 请求接口名
   *  params 请求参数数据对象
   */
  getversionURL(){
    return versionURL;
  },
  getIsSound(){
    return isSound;
  },
  setIsSound(data){
    isSound=data;
  },
  getfileURL(){
    return fileURL;
  },
  setfileURL(data){
    fileURL=data;
  },
  getToken(){
    return token;
  },
  setToken(data){
    token=data;
  },
  getBoxUrl(){
    return boxUrl;
  },
  setBoxUrl(data){
    boxUrl = data;
  },
  getOutTime(){
    return outTime;
  },
  getOpenMack(){
    return openMack;
  },
  setOpenMack(data){
    openMack=data;
  },
  getV() {
    return version;
  },
  getURL() {
    return BASE_URL;
  },
  xhr(url, params) {
    console.log('请求', url, '123', params);
    if (params) {
    } else {
      var params = {};
    }
    //    console.log(params)
    return new Promise((resolve, reject) => {
      fetch(url, {
        method: 'POST', //定义请求方式，POST、GET、PUT等
        headers: {
          Accept: 'application/json, text/plain, */*', // 提交参数的数据方式,这里以json的形式
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(params), //提交的参数
      })
        .then(response => response.json()) //数据解析的方式，json解析
        .then(responseJson => {
          var code = responseJson.code; //返回直接映射完的数据，可以直接使用
          resolve(responseJson);
          // alert(JSON.stringify(responseJson))
        })
        .catch(error => {
          Alert.alert('请求错误', '网络请求问题');
          console.error(error);
        });
    });
  },
  xhrLAN(url, params) {
    console.log('请求', BASE_URLLAN+url, '123', params);
    if (params) {
    } else {
      var params = {};
    }
    //    console.log(params)
    return new Promise((resolve, reject) => {
      fetch(BASE_URLLAN + url, {
        method: 'POST', //定义请求方式，POST、GET、PUT等
        headers: {
          Accept: 'application/json, text/plain, */*', // 提交参数的数据方式,这里以json的形式
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(params), //提交的参数
      })
        .then(response => response.json()) //数据解析的方式，json解析
        .then(responseJson => {
          var code = responseJson.code; //返回直接映射完的数据，可以直接使用
          resolve(responseJson);
          // alert(JSON.stringify(responseJson))
        })
        .catch(error => {
          Alert.alert('请求错误', '网络请求问题');
          console.error(error);
        });
    });
  },
  //版本管理请求
  xhrVER(url,method,params) {
    console.log('请求',method, versionURL+url, '123', params);
    if (params) {
    } else {
      var params = {};
    }
    return new Promise((resolve, reject) => {
      fetch(versionURL + url, {
        method: method, //定义请求方式，POST、GET、PUT等
        headers: {
          Accept: 'application/json, text/plain, */*', // 提交参数的数据方式,这里以json的形式
          'Content-Type': 'application/json',
        },
        body: method=="GET"?"":JSON.stringify(params), //提交的参数
      }).then(response => response.json()) //数据解析的方式，json解析
        .then(responseJson => {
          var code = responseJson.code; //返回直接映射完的数据，可以直接使用
          resolve(responseJson);
          
        })
        .catch(error => {
          Alert.alert('请求错误', '网络请求问题');
          console.error(error);
        });
    });
  },
  //时间戳转换1.formatDateTime("时间戳","yyyy-MM-dd")
  //2.formatDateTime("时间戳","yyyyMMdd")
  formatDateTime(time, format) {
    var t = new Date(time);
    var tf = function(i) {
      return (i < 10 ? '0' : '') + i;
    };
    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
      switch (a) {
        case 'yyyy':
          return tf(t.getFullYear());
          break;
        case 'MM':
          return tf(t.getMonth() + 1);
          break;
        case 'mm':
          return tf(t.getMinutes());
          break;
        case 'dd':
          return tf(t.getDate());
          break;
        case 'HH':
          return tf(t.getHours());
          break;
        case 'ss':
          return tf(t.getSeconds());
          break;
      }
    });
  },
  getboxsize(size) {
    switch (size) {
      case 'S': {
        return '小箱';
        break;
      }
      case 'L': {
        return '大箱';
        break;
      }
      case 'M': {
        return '中箱';
        break;
      }
      case 'XL': {
        return '超大箱';
        break;
      }
      case 'XS': {
        return '超小箱';
        break;
      }
    }
  },
  getBoxCode(num){
      var num = num.split("");
      var str=""
      if(num[num.length-1]=="G"){
        str=str+num[num.length-3]
        str=str+num[num.length-2]
       return parseInt(str,16)
      }

  },
  // openSound(url){
    
  //   // console.log(whoosh,"----------------")
  //   // whoosh?whoosh.release():""
  //   if(isSound){ 
  //    whoosh = new Sound(url, (error) => {
  //     if (error) {
  //       console.log('failed to load the sound', error);
  //       return;
  //     }
  //     whoosh.play((success) => {
       
        
  //         if (success) {
  //           console.log('播放成功');
  //           whoosh.release()
  //           // setTimeout(()=>{
  //           //   whoosh.release()
  //           // },1000)
           
  //         } else {
  //           console.log('playback failed due to audio decoding errors');
  //         }
  //       });
  //   });
  // }
  // }


  //声音为题
  openSound(url){
    console.log(url,"播放路径")
    if(isSound){
    var whoosh = new Sound(url, (error) => {
      if (error) {
        console.log('failed to load the sound', error);
        return;
      }
     
      whoosh.play((success) => {
        setTimeout(()=>{
          whoosh.release()
        },1000)
        
          if (success) {
            console.log('播放成功');
           
          } else {
            console.log('playback failed due to audio decoding errors');
          }
        });
    
     
    });
  }
  },
  //播放声音
  // openMusic(soundName){
  //   if(isSound){
  //     // NativeModules.OpenNativeModule.renusic(soundName)
  //     // return;
  //     if(soundName=='di'){
  //       // NativeModules.OpenNativeModule.renusicTwo()
  //       if(isSound){
  //          whoosh = new Sound(require('./../res/di.mp3'), (error) => {
  //           if (error) {
  //             console.log('failed to load the sound', error);
  //             return;
  //           }
           
  //           whoosh.play((success) => {
  //             setTimeout(()=>{
  //               whoosh.release()
  //             },10)
              
  //               if (success) {
  //                 console.log('播放成功');
                 
  //               } else {
  //                 console.log('playback failed due to audio decoding errors');
  //               }
  //             });
          
           
  //         });
  //       }
  //     }else{
        
  //       NativeModules.OpenNativeModule.renusic(soundName)
  //     }
  //   }
  // },
  rewhoosh(){
    whoosh?whoosh.release():"";
    NativeModules.OpenNativeModule.reSoundPool()
  }
};

module.exports = utils;

// module.exports = xhr
