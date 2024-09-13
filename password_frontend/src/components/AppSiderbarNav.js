import { CBadge, CNavLink, CSidebarNav } from "@coreui/react";
import { NavLink } from "react-bootstrap";

const AppSiderbarNav = ({item}) => {
    const navLink = (name,icon,badge,indent = false) => {
        return(
            <>
            {
                icon? icon: indent && (
                    <span className="nav-icon">
                <span className="nav-icon-bullet"></span>
                    </span>
                )
            }
            {name && name}
            {badge && (
                <CBadge color={badge.color} className="ms-auto">
                {badge.text}
                </CBadge>
            )}
            </>
        )
    }
    
    

    const navItem = (item,index,indent = false) =>{
    const {component,name,badge,icon,...rest} = item;
    const Component = component;
    return(
        <Component as="div" key={index}>
            {
                rest.to || rest.href? 
                (<CNavLink {...(rest.to && {as:NavLink})} {...rest}>
                    {navLink(name,icon,badge,indent)}
                </CNavLink>
                ) 
                :
                (
                    navLink(name,icon,badge,indent)
                )
            }
        </Component>
    )
    }

    return(
        <CSidebarNav>
            {item && item.map((item,index) => navItem(item,index) ) }
        </CSidebarNav>
    )

}
export default AppSiderbarNav;