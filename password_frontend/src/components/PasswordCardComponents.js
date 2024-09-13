import React from "react";
const PasswordCardComponents = ({items,parentCallBack}) => {
    const hanldeOnClick = (e) => {
        const id = e.currentTarget.attributes["data-id"].value;
        parentCallBack(id);
    }

    const cardItem = (item,index) => {
        const {icon,id,service_name,username} = item;
        const dom_id = "bl-"+index;
        return(
            <>
            <div onClick={hanldeOnClick} id={dom_id} data-id={id}  className="d-flex align-items-center align-items-md-start align-items-xl-center">
                <div className="bs-icon-xl bs-icon-circle bs-icon-primary d-flex flex-shrink-0 justify-content-center align-items-center me-4 d-inline-block bs-icon xl">
                    {icon}
                </div>
                <div>
                    <h4>{service_name}</h4>
                    <p>{username}</p>
                </div>
            </div>
            <hr className="my-2" ></hr>
            </>
        )
    }

    return(
        <>
        {items && items.map((item,index) => 
        <React.Fragment key={index}>
            {cardItem(item,index)}
        </React.Fragment>
        )}
        </>
    )
}
export default PasswordCardComponents;