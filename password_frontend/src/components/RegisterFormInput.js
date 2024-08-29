import { faArrowRight, faKey, faLock, faMailBulk, faUser } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useState } from 'react';

const RegisterFormInput = () => {
    const [formData,setFormData] = useState({
        username: '',
        password: '',
        confirmPassword: '',
        masterKey: '',
        confirmMasterKey: '',
        emailAddress: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
          ...formData,
          [name]: value
        });
      };

      const handleRegister = (e) => {
        e.preventDefault();
        console.log(formData);
        fetch("/api/user/register",{
            method:"POST",
            headers: {
                "Content-Type": "application/json" // 設置 Content-Type 為 application/json
            },
            body:JSON.stringify(formData)
        }).then(res => res.json())
        .then(response => {
            console.log(response);
        })
        
      };
    

    return(
        <form className='login100-form validate-form'>
                    <span className='login100-form-title'>
                        Register
                    </span>
                    <div className='wrap-input100 validate-input'>
                        <input className='input100' type='text' name='username' placeholder='User' value={formData.username} onChange={handleChange} />
                        <span className='focus-input100'></span>
                        <span className='symbol-input100'>
                        <FontAwesomeIcon icon={faUser}></FontAwesomeIcon>
                        </span>
                    </div>
                    <div className='wrap-input100 validate-input'>
                        <input className='input100' type='password' name='password' placeholder='Password' value={formData.password} onChange={handleChange} />
                        <span className='focus-input100'></span>
                        <span className='symbol-input100'>
                        <FontAwesomeIcon icon={faLock}></FontAwesomeIcon>
                        </span>
                    </div>
                    <div className='wrap-input100 validate-input'>
                        <input className='input100' type='password' name='confirmPassword' placeholder='Confirm Password' value={formData.confirmPassword} onChange={handleChange}/>
                        <span className='focus-input100'></span>
                        <span className='symbol-input100'>
                        <FontAwesomeIcon icon={faLock}></FontAwesomeIcon>
                        </span>
                    </div>
                    <div className='wrap-input100 validate-input'>
                        <input className='input100' type='password' name='masterKey' placeholder='Master Key' value={formData.masterKey} onChange={handleChange}/>
                        <span className='focus-input100'></span>
                        <span className='symbol-input100'>
                        <FontAwesomeIcon icon={faKey}></FontAwesomeIcon>
                        </span>
                    </div>
                    <div className='wrap-input100 validate-input'>
                        <input className='input100' type='password' name='confirmMasterKey' placeholder='Confirm Master Key' value={formData.confirmMasterKey} onChange={handleChange}/>
                        <span className='focus-input100'></span>
                        <span className='symbol-input100'>
                        <FontAwesomeIcon icon={faKey}></FontAwesomeIcon>
                        </span>
                    </div>
                    <div className='wrap-input100 validate-input'>
                        <input className='input100' type='email' name='emailAddress' placeholder='Email' value={formData.emailAddress} onChange={handleChange}/>
                        <span className='focus-input100'></span>
                        <span className='symbol-input100'>
                        <FontAwesomeIcon icon={faMailBulk}></FontAwesomeIcon>
                        </span>
                    </div>
                    <div className='container-login100-form-btn'>
                        <button className='login100-form-btn' onClick={handleRegister}>
                            Register
                        </button>
                    </div>
                
                    <div className='text-center p-t-136'>
                    <a className='txt2' href='/loginForm'>
                    Back To Login
                    <FontAwesomeIcon icon={faArrowRight} className='m-l-5'></FontAwesomeIcon>
                    </a>
                    </div>
                </form>
    );
}

export default RegisterFormInput;