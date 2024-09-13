import {
    cilSpeedometer
} from '@coreui/icons';
import { CIcon } from "@coreui/icons-react";
import { Modal } from "react-bootstrap";
import { useModal } from "./ModalContext";
const DetailModalComponents = () => {
    const modalcontext = useModal();
    return(
        <>
        <Modal.Header closeButton onHide={modalcontext.closeModal}>
            <button  onClick={() => {modalcontext.updateState("default")}}>
            <CIcon icon={cilSpeedometer} customClassName="nav-icon" />
            back
            </button>
            <Modal.Title>New Item</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            {modalcontext.page}
        </Modal.Body>
        </>
    )

}
export default DetailModalComponents;