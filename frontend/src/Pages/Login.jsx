import {LoginComp} from "../components/RegisterLogin.jsx"
import { Link} from "react-router";


function LoginPage({ onLogin, token,loginFlag}){


return ( <div>
<LoginComp onLogin={onLogin} token={token} loginFlag={loginFlag} />

<Link to={"/register"}>I dont have an account </Link>

    </div>)}

export default LoginPage ;












