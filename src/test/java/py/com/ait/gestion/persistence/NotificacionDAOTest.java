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
package py.com.ait.gestion.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.ticpy.tekoporu.junit.DemoiselleRunner;

import py.com.ait.gestion.domain.Notificacion;
import py.com.ait.gestion.domain.Usuario;
import py.com.ait.gestion.persistence.NotificacionDAO;
import py.com.ait.gestion.persistence.UsuarioDAO;

@RunWith(DemoiselleRunner.class)
public class NotificacionDAOTest {

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
		notificacionDAO.insert(notificacion);		
		Notificacion n = notificacionDAO.load(notificacion.getNotificacionId());
		assertNotNull(n);
	}
	
	//@Test
	public void testUpdate() {
		
		Long id = notificacionDAO.getMaxId();
		Notificacion notificacion = notificacionDAO.load(id);
		Usuario usuario = usuarioDAO.load(3L);
		notificacion.setUsuario(usuario);
		notificacion.setDescripcion("Prueba actualizacion");
		notificacion.setTitulo("Prueba titulo actualizado");
		notificacion.setFechaMostrado(new Date());		
		notificacionDAO.update(notificacion);
		Notificacion n = notificacionDAO.load(id);
		assertNotNull(n);
		assertEquals(notificacion.getUsuario().getUsuario(), n.getUsuario().getUsuario());
		assertEquals(notificacion.getTitulo(), n.getTitulo());
	}
	
	//@Test
	public void testDelete() {
		
		Long id = notificacionDAO.getMaxId();
		notificacionDAO.delete(id);
		
	}
	
	//@Test
	public void testGetAlertasInicio() {
		
		System.out.println("==================ALERTAS DE INICIO======================");
		List<Notificacion> list = notificacionDAO.getAlertasInicio();
		for(Notificacion n : list) {
			
			System.out.println("=========================================================");
			System.out.println("Entidad: " + n.getEntidad());
			System.out.println("EntidadId: " + n.getEntidadId());
			System.out.println("Titulo: " + n.getTitulo());
			System.out.println("Descripcion: " + n.getDescripcion());
			System.out.println("Responsable: " + n.getResponsable().getUsuario());
			System.out.println("Usuario: " + n.getUsuario().getUsuario());
			System.out.println("Estado: " + n.getEstado());
			System.out.println("FechaCreacion: " + n.getFechaCreacion());
			System.out.println("Tipo: " + n.getTipo());
		}
		System.out.println("==================FIN ALERTAS INICIO======================");
	}
	
	//@Test
	public void testGetAlertasActividades() {
		
		System.out.println("==================ALERTAS ACTIVIDADES======================");
		List<Notificacion> list = notificacionDAO.getAlertasActividades();
		for(Notificacion n : list) {
			
			System.out.println("=========================================================");
			System.out.println("Entidad: " + n.getEntidad());
			System.out.println("EntidadId: " + n.getEntidadId());
			System.out.println("Titulo: " + n.getTitulo());
			System.out.println("Descripcion: " + n.getDescripcion());
			System.out.println("Responsable: " + n.getResponsable().getUsuario());
			System.out.println("Usuario: " + n.getUsuario().getUsuario());
			System.out.println("Estado: " + n.getEstado());
			System.out.println("FechaCreacion: " + n.getFechaCreacion());			
		}
		System.out.println("==================FIN ACTIVIDADES======================");
	}
	
	//@Test
	public void testGetAlarmasActividades() {
		
		System.out.println("==================ALARMAS ACTIVIDADES======================");
		List<Notificacion> list = notificacionDAO.getAlarmasActividades();
		for(Notificacion n : list) {
			
			System.out.println("=========================================================");
			System.out.println("Entidad: " + n.getEntidad());
			System.out.println("EntidadId: " + n.getEntidadId());
			System.out.println("Titulo: " + n.getTitulo());
			System.out.println("Descripcion: " + n.getDescripcion());
			System.out.println("Responsable: " + n.getResponsable().getUsuario());
			System.out.println("Usuario: " + n.getUsuario().getUsuario());
			System.out.println("Estado: " + n.getEstado());
			System.out.println("FechaCreacion: " + n.getFechaCreacion());			
		}
		System.out.println("==================FIN ACTIVIDADES======================");
	}
	
	//@Test
	public void testGetAlertasReprogramacionProcesos() {
		
		System.out.println("==================ALERTAS REPROGAMACION PROCESOS======================");
		List<Notificacion> list = notificacionDAO.getAlertasReprogramacionProcesos();
		for(Notificacion n : list) {
			
			System.out.println("=========================================================");
			System.out.println("Entidad: " + n.getEntidad());
			System.out.println("EntidadId: " + n.getEntidadId());
			System.out.println("Titulo: " + n.getTitulo());
			System.out.println("Descripcion: " + n.getDescripcion());
			System.out.println("Responsable: " + n.getResponsable().getUsuario());
			System.out.println("Usuario: " + n.getUsuario().getUsuario());
			System.out.println("Estado: " + n.getEstado());
			System.out.println("FechaCreacion: " + n.getFechaCreacion());			
		}
		System.out.println("==================FIN PROCESOS======================");
	}
	
	//@Test
	public void testGetNotificaciones() {
		
		System.out.println("==================NOTIFICACIONES======================");
		String usuarioNotificaciones = "admin";
		String periodoCorte = "2S";
		List<Notificacion> list = notificacionDAO.getNotificaciones(usuarioNotificaciones, periodoCorte);
		for(Notificacion n : list) {
			
			System.out.println("=====================ID: "+n.getNotificacionId()+"======================");
			System.out.println("Entidad: " + n.getEntidad());
			System.out.println("EntidadId: " + n.getEntidadId());
			System.out.println("Titulo: " + n.getTitulo());
			System.out.println("Descripcion: " + n.getDescripcion());
			System.out.println("Responsable: " + n.getResponsable().getUsuario());
			System.out.println("Usuario: " + n.getUsuario().getUsuario());
			System.out.println("Estado: " + n.getEstado());
			System.out.println("FechaCreacion: " + n.getFechaCreacion());			
		}
		System.out.println("==================FIN NOTIFICACIONES======================");
	}
	
	@Test
	public void testInsertar() {
		
		List<Notificacion> notificaciones = notificacionDAO.getAlertasInicio();
		boolean inserted;
		for(Notificacion notificacion : notificaciones) {
		
			inserted = notificacionDAO.insertar(notificacion);
			System.out.println(">>>>>Inserted: " + inserted);
		}
	}
}
