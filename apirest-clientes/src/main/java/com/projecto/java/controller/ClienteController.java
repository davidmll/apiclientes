package com.projecto.java.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.projecto.java.model.Cliente;
import com.projecto.java.model.Region;
import com.projecto.java.service.ClienteService;

@RestController
@RequestMapping("/api")
public class ClienteController {

	@Autowired
	private ClienteService service;

//	Methods Get

	@GetMapping({ "/clientes", "/todos" })
	public List<Cliente> index() {
		return service.findAllClientes();
	}

	@GetMapping("clientes/{id}")
	public ResponseEntity<?> findClienteById(@PathVariable int id) {

		Cliente cliente = null;

		Map<String, Object> response = new HashMap<>();

		try {

			cliente = service.findById(id);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cliente == null) {
			response.put("mensaje", "El cliente ID: " + id + " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);

	}

//	Method Post

	@PostMapping("/cliente")
	public ResponseEntity<?> saveCliente(@RequestBody Cliente cliente) {
		Cliente clienteNew = null;

		Map<String, Object> response = new HashMap<>();

		try {

			clienteNew = service.saveCliente(cliente);

		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar un insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", clienteNew);
		response.put("cliente", "El cliente ha sido creado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

//	Method Put

	@PutMapping("/cliente/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> updateCliente(@RequestBody Cliente cliente, @PathVariable int id) {

		Cliente clienteUpdate = service.findById(id);

		Map<String, Object> response = new HashMap<>();

		if (clienteUpdate == null) {
			response.put("mensaje", "Error: no se puedo editar el cliente con ID: " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			clienteUpdate.setIdcliente(id);
			clienteUpdate.setApellido(cliente.getApellido());
			clienteUpdate.setCreatedAt(cliente.getCreatedAt());
			clienteUpdate.setEmail(cliente.getEmail());
			clienteUpdate.setNombre(cliente.getNombre());
			clienteUpdate.setTelefono(cliente.getTelefono());

			service.saveCliente(clienteUpdate);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al realizar un update en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("cliente", clienteUpdate);
		response.put("mensaje", "El cliente ha sido actualizado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

//	Method Delete

	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<?> deleteCliente(@PathVariable int id) {

		Cliente clienteDelete = service.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (clienteDelete == null) {
			response.put("mensaje", "Error: no se pudo eliminar el cliente con ID: " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			String nombreFotoAnterior = clienteDelete.getImagenes();

			if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();

				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			service.deleteCliente(id);
		} catch (DataAccessException e) {

			response.put("mensaje", "Error al eliminar la información en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("cliente", clienteDelete);
		response.put("mensaje", "El cliente ha sido eliminado con éxito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PostMapping("/cliente/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") int id) {

		Map<String, Object> response = new HashMap<>();

		Cliente cliente = service.findById(id);

		if (!archivo.isEmpty()) {
//			String nombreArchivo = archivo.getOriginalFilename();
			String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);

			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreFotoAnterior = cliente.getImagenes();

			if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();

				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			cliente.setImagenes(nombreArchivo);
			service.saveCliente(cliente);

			response.put("cliente", cliente);
			response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	@GetMapping("/uploads/imagen/{nombreImagen:.+}")
//	.+ para poder acceder a la png,jpeg,etc
	public ResponseEntity<Resource> verImagen(@PathVariable String nombreImagen){
		
		Path rutaImagen = Paths.get("uploads").resolve(nombreImagen).toAbsolutePath();
		
		Resource recurso = null;
		
		try {
			recurso = new UrlResource(rutaImagen.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error no se puede cargar la imagen "+nombreImagen);
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\" "+recurso.getFilename()+"\"");
		
		return new ResponseEntity<Resource>(recurso,cabecera, HttpStatus.OK);
	}
	
	@GetMapping("/clientes/regiones")
	public List<Region> listarRegiones(){
		return service.findAllRegiones();
	}

}
