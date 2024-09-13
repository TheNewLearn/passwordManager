import AppContent from "../components/AppContent";
import AppHeader from "../components/AppHeader";
import { ModalProvider } from "../components/modal/ModalContext";
import SiderBar from "../components/SiderBar";
const Defaultlayout = () => {
    return(
        <div>
        <SiderBar/>
        <div className="wrapper d-flex flex-column min-vh-100">
            <ModalProvider>
            <AppHeader />
            </ModalProvider>
            <div className="body flex-grow-1">
                <AppContent />
            </div>
        </div>
        </div>
    )
}
export default Defaultlayout;