import axiosInstance from "@/service/api";
import CustomError from "@/error/CustomError";

const setup = (router) => {
    axiosInstance.interceptors.request.use(
        (config) => {
            if(config.url!='/sign-in' && !config.url!='/sign-up'){
                console.log(
                    "axiosInstance.interceptors.request >\n" + "URL: " + config.url);

                const token = localStorage.getItem('accessToken');
                //config.headers['Refresh-Token'] = 'Bearer '+localStorage.getItem('refreshToken');

                if (token) {
                    // for Spring Boot back-end
                    config.headers["Authorization"] = 'Bearer ' + token;
                }
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    axiosInstance.interceptors.response.use(
        (res) => {
            const originalConfig = res.config;
            console.log("axiosInstance.interceptors.response >\n" +
                "URL: " + originalConfig.url+"\n");

            if(originalConfig.url=='/sign-in'){
                const returnData = {...res.data};
                localStorage.clear();
                // 로컬 스토리지에 저장
                localStorage.setItem("accessToken",returnData.data.accessToken);
                localStorage.setItem("refreshToken",returnData.data.refreshToken);
            }

            if(originalConfig.url=='/sign-out'){
                localStorage.clear();
                router.push({name: 'Home'});
            }

            if(res.headers['newtoken']){
                const newAccessToken = res.headers['newtoken'];
                localStorage.removeItem("accessToken");
                localStorage.setItem("accessToken",newAccessToken);
            }

            return res;
        },
        async (err) => {
            const originalConfig = err.config;
            console.log("axiosInstance.interceptors.response >\n" +
                "URL: " + originalConfig.url+"\n"+
                "ERR STATUS: " + err.response.status);

            if(!err.response){
                console.log("network error!");
                const customErr = new CustomError("Network Error가 발생했습니다. 운영자에게 문의 하세요.","ERR-SERVER-XXX","400");
                console.log(customErr.response.data.code);
                return Promise.reject(customErr);
            }

            if(originalConfig.url!='/sign-in' && !originalConfig.url!='/sign-up' && err.response){
                if (err.response.status === 401) {
                    try {
                        console.log(err.response.data.status,err.response.data.code , err.response.data.message);
                        if(err.response.data.code==='ERR-SERVER-6'){
                            console.log("@@@@@@@@@@@@ 로그인 해야 함 @@@@@@@@@@@@");
                            console.log("@@@@@@@@@@@@ 로그인 페이지 처리 미구현 @@@@@@@@@@@@");
                            router.push({name: 'Login'});
                            // 이행되지 않는 Promise를 반환하여 Promise Chaining 끊어주기
                            return Promise.reject(err);
                        }
                        originalConfig._retry = true;
                        originalConfig.headers['Refresh-Token'] = 'Bearer ' + localStorage.getItem('refreshToken');
                        return axiosInstance(originalConfig);
                    }catch(err){
                        if (err.response && err.response.data) {
                            return Promise.reject(err.response.data);
                        }
                        return Promise.reject(err);
                    }
                }

                if (err.response.status === 403) {
                    router.push({name: 'Home'});
                    const customErr = new CustomError("접근 권한이 없네. 미안하다.","CODE-INFO","403");
                    console.log(customErr.response.data.code);
                    return Promise.reject(customErr);
                }
            }
            console.log(err.response.status);
            return Promise.reject(err);
        }
    );
};

export default setup;