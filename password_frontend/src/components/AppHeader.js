import { cibAddthis } from "@coreui/icons";
import CIcon from "@coreui/icons-react";
import { CButton, CContainer, CHeader, CHeaderNav } from "@coreui/react";
import { useModal } from "./modal/ModalContext";
import ModalController from "./modal/ModalController";

const AppHeader = () =>{
    const { openModal,updateState } = useModal();
    const addItem = () => {
        openModal();
        updateState("default");
    }
    return(
        <CHeader position="sticky" className="p-0">
           <CContainer className="border-bottom px-4" fluid>
           <CHeaderNav className="ms-auto">
               
                    <CButton color="primary" onClick={addItem}>
                        <CIcon icon={cibAddthis} className="me-2"/>
                            Add Item
                    </CButton>
                </CHeaderNav>
           </CContainer>
           <ModalController />
        </CHeader>
       
    )
}
export default AppHeader;