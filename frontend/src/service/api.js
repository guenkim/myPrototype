import axios from "axios";

const AxiosInst = axios.create({
    baseURL : 'http://localhost:8082/api/',
    headers: {
        "Content-Type": "application/json",
    }
})

/**
AxiosInst.interceptors.request.use(
    (config) => {
        if(config.url!='/sign-in' && !config.url!='/sign-up'){
            config.headers['Authorization'] = 'Bearer '+localStorage.getItem('accessToken');
            //config.headers['Refresh-Token'] = 'Bearer '+localStorage.getItem('refreshToken');
        }
        config.headers['Access-Control-Allow-Origin'] = '*';  // CORS 설정(모든 리소스 허용)
        return config;
    },
    (error) => {
        console.log("1");
    return Promise.reject(error);
}
);


AxiosInst.interceptors.response.use(
    (res) => {
        const originalConfig = res.config;
        const returnData = {...res.data};
        if(originalConfig.url=='/sign-in'){
            // 로컬 스토리지에 저장
            localStorage.setItem("accessToken",returnData.data.accessToken);
            //localStorage.setItem("refreshToken",returnData.data.refreshToken);
            // localStorage.getItem(key); // 데이터 조회
            // localStorage.removeItem(key); // 키에 해당되는 데이터 삭제
            // localStorage.clear(); // 저장소 데이터 전체 삭제
        }
        console.log("res.status1:"+ res.status);
        return res;
    },
    async (error) => {
        if (axios.isAxiosError(error)) {
            console.log(error.response?.status);
            if (error.response) {
                console.error("에러 상태 코드:", error.response.status);
                console.error("에러 응답 데이터:", error.response.data);
            } else {
                console.error("에러 응답 없음");
            }
        }
        return Promise.reject(error);
    }

);
**/
export default AxiosInst;