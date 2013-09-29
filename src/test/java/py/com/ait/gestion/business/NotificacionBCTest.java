/*
 * TICPY Framework
 * Copyright (C) 2012 Plan Director TICs
 *
----------------------------------------------------------------------------
 * Originally developed by SERPRO
 * Demoiselle Framework
 * Copyright (C) 2010 SERPRO
 *
----------------------------------------------------------------------------
 * This file is part of TICPY Framework.
 *
 * TICPY Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 *
----------------------------------------------------------------------------
 * Este archivo es parte del Framework TICPY.
 *
 * El TICPY Framework es software libre; Usted puede redistribuirlo y/o
 * modificarlo bajo los términos de la GNU Lesser General Public Licence versión 3
 * de la Free Software Foundation.
 *
 * Este programa es distribuido con la esperanza que sea de utilidad,
 * pero sin NINGUNA GARANTÍA; sin una garantía implícita de ADECUACION a cualquier
 * MERCADO o APLICACION EN PARTICULAR. vea la GNU General Public Licence
 * más detalles.
 *
 * Usted deberá haber recibido una copia de la GNU Lesser General Public Licence versión 3
 * "LICENCA.txt", junto con este programa; en caso que no, acceda a <http://www.gnu.org/licenses/>
 * o escriba a la Free Software Foundation (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package py.com.ait.gestion.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.ticpy.tekoporu.junit.DemoiselleRunner;

import py.com.ait.gestion.business.NotificacionAgentBC;
import py.com.ait.gestion.domain.Notificacion;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.persistence.NotificacionDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@RunWith(DemoiselleRunner.class)
public class NotificacionBCTest {

	@Inject
	private NotificacionAgentBC notificacionBC;
	
	@Inject
	private NotificacionDAO notificacionDAO;
	
	@Inject
	private UsuarioDAO usuarioDAO;
		
	@Before
	public void before() {

		
	}
	
	//@Test
	public void testInsert() {
		
		Usuario usuario = usuarioDAO.load(1L);
		Usuario responsable = usuario;//usuarioDAO.load(1L);
		Notificacion notificacion = new Notificacion("ACTIVIDAD", 1L, "N", usuario, responsable, null, new Date(), "A", "Test", "Test de notificacion");		
		notificacionBC.insert(notificacion);		
		Notificacion n = notificacionBC.load(notificacion.getNotificacionId());
		assertNotNull(n);
	}
	
	//@Test
	public void testUpdate() {
				
		Long id = notificacionDAO.getMaxId();
		Notificacion notificacion = notificacionBC.load(id);
		Usuario usuario = usuarioDAO.load(3L);
		notificacion.setUsuario(usuario);
		notificacion.setDescripcion("Prueba actualizacion");
		notificacion.setTitulo("Prueba titulo actualizado");
		notificacion.setFechaMostrado(new Date());		
		notificacionBC.update(notificacion);
		Notificacion n = notificacionBC.load(id);
		assertNotNull(n);
		assertEquals(notificacion.getUsuario().getUsuario(), n.getUsuario().getUsuario());
		assertEquals(notificacion.getTitulo(), n.getTitulo());
	}
	
	//@Test
	public void testDelete() {
		
		Long id = notificacionDAO.getMaxId();
		notificacionBC.delete(id);
		
	}
	
	@Test
	public void testExecute() {
		
		notificacionBC.execute();
	}
	
}
