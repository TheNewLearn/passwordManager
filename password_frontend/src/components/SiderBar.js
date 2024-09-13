import { CIcon } from '@coreui/icons-react';
import { CSidebar, CSidebarBrand, CSidebarFooter, CSidebarHeader } from '@coreui/react';
import navigation from '../_nav';
import { logo } from '../assets/brand/logo';
import { sygnet } from '../assets/brand/sygnet';
import AppSiderbarNav from './AppSiderbarNav';

const SiderBar = () => {
  const unfoldable = true; 
    return(
        <CSidebar 
        className="border-end"
        colorScheme="dark"
        position="fixed"
        unfoldable={unfoldable}
        >
  <CSidebarHeader className="border-bottom">
        <CSidebarBrand to="/">
          <CIcon customClassName="sidebar-brand-full" icon={logo} height={32} />
          <CIcon customClassName="sidebar-brand-narrow" icon={sygnet} height={32} />
        </CSidebarBrand>
      </CSidebarHeader>
      <AppSiderbarNav item={navigation} />
      
  <CSidebarFooter className="border-top d-none d-lg-flex">
      </CSidebarFooter>
</CSidebar>
    )
}

export default SiderBar;