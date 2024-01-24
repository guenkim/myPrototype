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
      '/api':{
        "target":'http://localhost:8082', // Spring boot의 주소 및 포트
        changeOrigin: true,
        ws:true
      }
    }
  }
}
