import { faArrowRight, faLock, faUser } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useState } from 'react';
import { useNavigate } from "react-router-dom";
const LoginFormsInput = () => {
    const navigate = useNavigate();
    const [formData,setFormData] = useState({
        username: '',
        password: '',
        masterKey: '',
    });
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
          ...formData,
          [name]: value
        });
      };

     const handleLogin = (e) => {
        e.preventDefault();
        fetch("/api/user/auth/login",{
            method:"POST",
            headers: {
                "Content-Type": "application/json" // 設置 Content-Type 為 application/json
            },
            body:JSON.stringify(formData)
        }).then(res => res.json())
        .then(resp => {
            if(resp.statusCode === 200 && resp.data != null){
                localStorage.setItem("jwtToken",resp.data);
                navigate("/");
            }else{
                navigate("/loginForms");
            }
        })
     } 

    return(
        <form className='login100-form validate-form'>
                    <span className='login100-form-title'>
                        Member Login
                    </span>
                    <div className='wrap-input100 validate-input'>
                        <input className='input100' type='text' name='username' placeholder='User' onChange={handleChange} value={formData.username} />
                        <span className='focus-input100'></span>
                        <span className='symbol-input100'>
                        <FontAwesomeIcon icon={faUser}></FontAwesomeIcon>
                        </span>
                    </div>
                    <div className='wrap-input100 validate-input'>
                        <input className='input100' type='password' name='password' placeholder='Password' onChange={handleChange} value={formData.password} />
                        <span className='focus-input100'></span>
                        <span className='symbol-input100'>
                        <FontAwesomeIcon icon={faLock}></FontAwesomeIcon>
                        </span>
                    </div>
                    <div className='wrap-input100 validate-input'>
                        <input className='input100' type='password' name='masterKey' placeholder='Master Key' onChange={handleChange} value={formData.masterKey} />
                        <span className='focus-input100'></span>
                        <span className='symbol-input100'>
                        <FontAwesomeIcon icon={faLock}></FontAwesomeIcon>
                        </span>
                    </div>
                    <div className='container-login100-form-btn'>
                        <button className='login100-form-btn' onClick={handleLogin}>
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