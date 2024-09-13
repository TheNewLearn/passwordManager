import 'bootstrap/dist/css/bootstrap.css';
import LoginFormsInput from "../components/LoginFormsInput";
import RegisterFormInput from '../components/RegisterFormInput';
import './Main.css';
const AuthForms = ({mode}) => {
    return(
        <div className='container-login100'>
            <div className='wrap-login100'>
                <div className='login100-pic js-tilt loginform'>
                    <img alt='logo' src="logo192.png" />
                </div>
                {mode === 'login' ? (<LoginFormsInput/>) : (<RegisterFormInput/>)}
            </div>
        </div>
    )
}
export default AuthForms;