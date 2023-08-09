import {useState} from 'react';
import Hamburger from "hamburger-react";
import {NavLink} from "react-router-dom";

function BurgerMenu() {
    const [isOpen, setOpen] = useState<boolean>(false)
    return (<>
            <Hamburger toggled={isOpen}
                       toggle={setOpen}
                       direction={"right"}
                       color={"black"}
                       label="Show menu"/>
            <section className={`off-canvas-menu ${isOpen ? "menu-open" : "menu-closed"}`}>
                <nav className={"navbar-container"}>
                    <ul className={"navbar-list"}>
                        <li>Add FoodSpots</li>
                        <NavLink to={"/"}
                                 className={({ isActive }) =>
                                     isActive ? "active-link" : ""
                                 }>
                            <li>Home</li>
                        </NavLink>
                        <li>Map</li>
                        <li>My Account</li>
                    </ul>
                </nav>
            </section>
        </>

    );
}

export default BurgerMenu;