import AxiosInst from "@/service/api";

class AuthService {


    signIn(data){
        return AxiosInst.post('/sign-in',data);
    }

    signUp(data){
        return  AxiosInst.post('/sign-up',data);
    }

}

export default new AuthService();
