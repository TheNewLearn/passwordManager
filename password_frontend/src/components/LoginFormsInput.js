import { faArrowRight, faLock, faUser } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
const LoginFormsInput = () => {
    return(
        <form className='login100-form validate-form'>
                    <span className='login100-form-title'>
                        Member Login
                    </span>
                    <div className='wrap-input100 validate-input'>
                        <input className='input100' type='text' name='username' placeholder='User' />
                        <span className='focus-input100'></span>
                        <span className='symbol-input100'>
                        <FontAwesomeIcon icon={faUser}></FontAwesomeIcon>
                        </span>
                    </div>
                    <div className='wrap-input100 validate-input'>
                        <input className='input100' type='password' name='password' placeholder='Password' />
                        <span className='focus-input100'></span>
                        <span className='symbol-input100'>
                        <FontAwesomeIcon icon={faLock}></FontAwesomeIcon>
                        </span>
                    </div>
                    <div className='container-login100-form-btn'>
                        <button className='login100-form-btn'>
                            Login
                        </button>
                    </div>
                    <div className='text-center p-t-12'>
                        <span className='txt1'>Forgot </span>
                        <a className='txt2' href='/'>Username / Password?</a>
                    </div>
                    <div className='text-center p-t-136'>
                    <a className='txt2' href='/registerForm'>
                    Create your Account
                    <FontAwesomeIcon icon={faArrowRight} className='m-l-5'></FontAwesomeIcon>
                    </a>
                    </div>
                </form>
    );
}

export default LoginFormsInput;