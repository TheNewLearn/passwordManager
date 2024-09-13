import {
    cilSpeedometer
} from '@coreui/icons';
import CIcon from '@coreui/icons-react';
import { CNavItem } from '@coreui/react';
const _nav = [
    {
        component:CNavItem,
        name: 'Dashboard',
        href:'/loginForm',
        icon: <CIcon icon={cilSpeedometer} customClassName="nav-icon" />,
        badge: {
            color: 'info',
            text: 'NEW',
          },
    }
]
export default _nav;