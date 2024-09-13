import { Modal } from "react-bootstrap";
import _item from "../../_item";
import ItemSelector from "../ItemSelector";
import { useModal } from "./ModalContext";
const DefaultModalComponents = () => {

    const modalcontext = useModal();
    return(
        <>
        <Modal.Header closeButton onHide={modalcontext.closeModal} >
            <Modal.Title>
                Select Item
                </Modal.Title> 
        </Modal.Header>
        <Modal.Body>
           <ItemSelector items={_item} />
        </Modal.Body>
        </>
    )
}
export default DefaultModalComponents;