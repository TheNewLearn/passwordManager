import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
const SweetAlertComponents = ({icon,title,showConfirmButton,timer,callback}) => {
    withReactContent(Swal).fire({
        title:title,
        icon:icon,
        showConfirmButton:showConfirmButton,
        timer:timer
    }).then(callback);

}
export default SweetAlertComponents;