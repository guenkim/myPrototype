import axios from 'axios';

/**
export default axios.create({
    baseURL: 'http://localhost:8082/api/'
});
 **/

const AxiosInst = axios.create({
    baseURL : 'http://localhost:8082/api/'
})

AxiosInst.interceptors.request.use(
    (config) => {
        config.headers['Access-Control-Allow-Origin'] = '*';  // CORS 설정(모든 리소스 허용)
        return config;
    }
)

export default AxiosInst;