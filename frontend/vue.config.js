/**
const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    proxy : 'http://localhost:8082'
  }
})
**/

module.exports = {
  devServer: {
    proxy: {
      '/':{
        "target":'http://localhost:8082/api/', // Spring boot의 주소 및 포트
        //"pathRewrite":{'^/':''},
        "changeOrigin":true,
        ws:false
      }
    }
  }
}
