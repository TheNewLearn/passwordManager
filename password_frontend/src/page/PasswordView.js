import { useEffect, useState } from "react";
import PasswordCardComponents from "../components/PasswordCardComponents";
import { API_Fetch_All_Password } from "../global/ConstValue";
import PasswordDetailView from "./PasswordDetailView";
const PasswordView = () => {
    const [selectedId, setSelectedId] = useState("");
    const [item,setItem] = useState("");

    const handleCardClick = (id) => {
        setSelectedId(id);
    };
    useEffect(
        () => {
            fetch(API_Fetch_All_Password,{
                method:"GET",
                headers:{
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + sessionStorage.getItem("jwtToken"),
                }
            }).then(res => res.json())
            .then(resp => {
                console.log(resp);
                setItem(resp.data);
            })
            .catch(
                () => {
                    sessionStorage.clear();
                    window.location.reload();
                }
            )
        },
        []
    )

    return(
        <div className="row min-vh-100">
            <div className="col-md-4 col-xxl-3 border border-2 border-top-0">
                <PasswordCardComponents items={item} parentCallBack={handleCardClick}  />
            </div>
            <div className="col-md-8">
                <PasswordDetailView id={selectedId} />
            </div>
        </div>
    )
}
export default PasswordView;