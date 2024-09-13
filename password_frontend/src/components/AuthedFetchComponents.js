import SweetAlertComponents from "./SweetAlertComponents";
const AuthedFetchComponents = async (url,method,formdata,successCallback,failCallback) => {
try{
 const response = await fetch(url,{
    method:method,
    headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + sessionStorage.getItem("jwtToken"),
      },
      body:JSON.stringify(formdata)
 });
 if(response.status === 403){
    sessionStorage.clear();
    window.location.reload();
 }

  const data = await response.json();
  if(data.statusCode === 200){
    SweetAlertComponents({
        title:data.rspMsg,
        icon:"success",
        showConfirmButton: false,
        timer: 2000, 
        callback:() => {successCallback(data)}
    });
  }  
  successCallback(data);
}catch(error){
    failCallback(error);
}
}
export default AuthedFetchComponents;