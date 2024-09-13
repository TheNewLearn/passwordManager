import { createContext, useContext, useState } from "react";
const ModalContext = createContext({
    isOpen:false,
    state:"default",
    page:"",
    openModal:() => {},
    closeModal: () => {},
    updateState: (newState) => {},
    updatePage: (newPage) => {}
});


export const ModalProvider = ({ children }) => {
    const [isOpen, setIsOpen] = useState(false);
    const [state, setState] = useState("default");
    const [page, setPage] = useState(null);

   
    const openModal = () => {setIsOpen(true)};


    const closeModal = () => setIsOpen(false);


    const updateState = (newState) => setState(newState);


    const updatePage = (newPage) => setPage(newPage);

    return (
        <ModalContext.Provider value={{ isOpen, state, page, openModal, closeModal, updateState, updatePage }}>
            {children}
        </ModalContext.Provider>
    );
};

export const useModal = () => useContext(ModalContext);
export default ModalContext;

