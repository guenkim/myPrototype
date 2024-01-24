import axiosInstance from "@/service/api";
import axios from "axios";
// import TokenService from "./token.service";

const setup = () => {
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
                // 로컬 스토리지에 저장
                localStorage.setItem("accessToken",returnData.data.accessToken);
                localStorage.setItem("refreshToken",returnData.data.refreshToken);
            }
            return res;
        },
        async (err) => {
            const originalConfig = err.config;
            console.log(">>>>>>>>>>>>>>>>>>>>>>>>>");
            console.log(axios.isAxiosError(err));
            console.log("#######################");
            console.log(err.response.status);
            console.log("#######################");
            console.log("axiosInstance.interceptors.response >\n" +
                "URL: " + originalConfig.url);


            /**
            console.log("axiosInstance.interceptors.response >\n" +
                "URL: " + originalConfig.url+"\n"+
                "ERR STATUS: " + err.response.status);
            **/


            return Promise.reject(err);
        }
    );
};

export default setup;