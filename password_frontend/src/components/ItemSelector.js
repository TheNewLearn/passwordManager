import { Button } from "react-bootstrap";
import { useModal } from "./modal/ModalContext";
const ItemSelector = ({items}) => {
    console.log(items);

    const modalcontext = useModal();

    const genButton = (name,page, color) => {
        return(
            <>
            <Button variant={color} onClick={() => 
                {
                    modalcontext.updatePage(page);
                    modalcontext.updateState("detail");
                }} size="lg">
                {name}
            </Button>
            </>
        )
    }

    const rows = () => {
        const elements = [];
        for(let i=0;i<items.length;i+=2){
            const {name,page} = items[i];
            elements.push(
                <div className="row mt-2" key={i} >
                    <div className="col">
                    {genButton(name,page,"primary")}
                    </div>
                    <div className="col">
                    { i+1 < items.length && genButton(items[i+1].name,items[i+1].page,"secondary") }
                    </div>
                </div>
            )
        }
        return elements;
    }

    return rows();
}

export default ItemSelector;