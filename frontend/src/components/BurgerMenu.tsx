import {useState} from 'react';
import Hamburger from "hamburger-react";

function BurgerMenu(props) {
    const [isOpen, setOpen] = useState<boolean>(false)
    return (<>
            <Hamburger toggled={isOpen}
                       toggle={setOpen}
                       direction={"right"}
                       color={"black"}
                       label="Show menu"
                       onToggle={toggled => {
                           if (toggled) {
                               console.log("Open")
                           } else {
                               console.log("Closed")
                           }
                       }}/>
            <section className={`off-canvas-menu ${isOpen ? "menu-open" : "menu-closed"}`}>

            </section>
        </>

    );
}

export default BurgerMenu;