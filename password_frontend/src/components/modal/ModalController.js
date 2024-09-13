import { Modal } from "react-bootstrap";
import DefaultModalComponents from "./DefaultModalComponents";
import DetailModalComponents from "./DetailModalComponents";
import { useModal } from "./ModalContext";
const ModalController = ({children}) => {
    const modalcontext = useModal();
   
    
     return(
        <Modal show={modalcontext.isOpen}>
          {modalcontext.state === "detail" ? <DetailModalComponents /> : <DefaultModalComponents />}
        </Modal>
    )
}

export default ModalController;