import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthedFetchComponents from "../components/AuthedFetchComponents";
import { useModal } from "../components/modal/ModalContext";
import { API_SAVEITEM } from "../global/ConstValue";
const AddPasswordForm = () => {
    const navigate = useNavigate();
    const modalcontext = useModal();
    const [formData,setFormData] = useState({
        service_name: '',
        username: '',
        password: '',
        service_url: '',
        type:"sites",
        key:sessionStorage.getItem("k1"),
    });
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
          ...formData,
          [name]: value
        });
      };

    
    const handleSuccess = (data) => {
        if(data.statusCode === 200){
            modalcontext.closeModal();
            window.location.reload();
        }
    }

    const handleError = (error) => {
        console.log(error);
    }


    const addItem = () => {
      AuthedFetchComponents(API_SAVEITEM,"POST",formData,handleSuccess,handleError);
    }  

    

    const inputElements = [
        {
            placeholder: "Service Name",
            name: "service_name",
        },
        {
            placeholder: "UserName",
            name: "username",
        },
        {
            placeholder: "Password",
            name: "password",
        },
        {
            placeholder: "Sites",
            name: "service_url",
        }
    ];


    const genInput = (placeholder, name) => {
        return (
            <div className="form-group mt-2" key={placeholder}>
                <input
                    className="form-control"
                    type="text"
                    placeholder={placeholder}
                    name={name}
                    onChange={handleChange} 
                />
            </div>
        );
    };


    return (
        <div className="container">
            <div className="card px-1 py-4">
                <div className="card-body">
                    <div className="row">
                        {inputElements.map((item) =>
                            genInput(item.placeholder, item.name)
                        )}
                    </div>
                    <div className="row">
                        <button onClick={addItem}>submit</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AddPasswordForm;
