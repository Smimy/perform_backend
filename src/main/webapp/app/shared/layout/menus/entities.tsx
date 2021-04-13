import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/workout-goal">
      Workout Goal
    </MenuItem>
    <MenuItem icon="asterisk" to="/workout">
      Workout
    </MenuItem>
    <MenuItem icon="asterisk" to="/exercise">
      Exercise
    </MenuItem>
    <MenuItem icon="asterisk" to="/exercise-type">
      Exercise Type
    </MenuItem>
    <MenuItem icon="asterisk" to="/serie">
      Serie
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
