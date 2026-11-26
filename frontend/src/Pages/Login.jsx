import {LoginComp} from "../components/RegisterLogin.jsx"
import { Link} from "react-router";


function LoginPage({ onLogin,loginFlag}){


return ( <div>
<LoginComp onLogin={onLogin}  loginFlag={loginFlag} />

<Link to={"/register"}>I dont have an account </Link>

    </div>)}

export default LoginPage ;












