import { useEffect, useState } from "react";
import AuthedFetchComponents from "../components/AuthedFetchComponents";
import { API_GET_PASSWORD, API_GET_PASSWORD_Decrypt } from "../global/ConstValue";

const PasswordDetailView = ({id}) => {
const [password,setPassword] = useState("");
const [decryptedPassword, setDecryptedPassword] = useState("");
const [decrypted,setDecrypted] = useState(false);
useEffect(
    () =>{
        if(id){
            setDecryptedPassword("");
            setDecrypted(false);
            fetch(API_GET_PASSWORD+id,
                {
                    method:"GET",
                    headers:{
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + sessionStorage.getItem("jwtToken"),
                    }
                }
            ).then(resp => resp.json())
            .then(resp => {
                setPassword(resp.data);
            });
        }       
    },[id]
);

const handleDecryptSuccess = (resp) => {
    if(resp.statusCode === 200){
        setDecryptedPassword(resp.data);
        setDecrypted(true);
    }
}


const handleDecryptPassword = () => {
    if (!password) return;
    if(decrypted) return;
    const data = {
        "ciphertext":password.password,
        "key":sessionStorage.getItem("k1")
    }
    AuthedFetchComponents(API_GET_PASSWORD_Decrypt,"POST",data,
        handleDecryptSuccess,
        null
    )
  };


  useEffect(() => {
    if(decryptedPassword && decrypted){
    navigator.clipboard.writeText(decryptedPassword);
    }
  },[decryptedPassword])

  

if (!password) return <div>Select a password to view details.</div>;



return(
    <>
    <div className="password-details">
      <h4>Service: {password.service_name}</h4>
      <p>URL: {password.service_url}</p>
      <p>Username: {password.username}</p>
      <p>
        Password: {decryptedPassword ? decryptedPassword : "********"}
      </p>
      <p>
      <button onClick={handleDecryptPassword} >
            decrypt & copy
        </button>
      </p>
      <p>Type: {password.type}</p>
    </div>
    </>
)    

}
export default PasswordDetailView;