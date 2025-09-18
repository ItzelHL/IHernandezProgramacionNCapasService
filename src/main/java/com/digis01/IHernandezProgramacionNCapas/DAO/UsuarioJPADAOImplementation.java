package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.DAO.UsuarioJPADAOImplementation.EstadoArchivo;
import com.digis01.IHernandezProgramacionNCapas.JPA.Colonia;
import com.digis01.IHernandezProgramacionNCapas.JPA.Direccion;
import com.digis01.IHernandezProgramacionNCapas.JPA.ErrorCM;
import com.digis01.IHernandezProgramacionNCapas.JPA.Result;
import com.digis01.IHernandezProgramacionNCapas.JPA.Rol;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPADAO 
{
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() 
    {
        Result result = new Result();
        try 
        {
            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario ORDER BY IdUsuario", Usuario.class);
            result.object = queryUsuario.getResultList();
            
            result.correct = true;
            result.status = 200;
        } 
        catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return result;
    }
    
    @Override
    public Result GetById(int idUsuario)
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuarioJPA = entityManager.find(Usuario.class, idUsuario);
            
            if (usuarioJPA != null) 
            {
                result.object = usuarioJPA;
                result.correct = true;
                result.status = 200;
            } else
            {
                result.errorMessage = "Usuario no encontrado, Id incorrecto";
                result.status = 400;
            }
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Transactional
    @Override
    public Result Add(Usuario usuario) 
    {
        Result result = new Result();
        
        try 
        {
            entityManager.persist(usuario);
            result.object = usuario;
            
            Direccion direccion = usuario.Direcciones.get(0);
            direccion.Usuario = usuario;
            direccion.Usuario.setIdUsuario(usuario.getIdUsuario());
            entityManager.persist(direccion);
            
            result.correct = true;
            result.status = 200;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        
        return result;
    }

    @Transactional
    @Override
    public Result Delete(int idUsuario) 
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            
            if (usuario != null) 
            {
                entityManager.remove(usuario);
                result.correct = true;
                result.status = 200;
            } else
            {
                result.errorMessage = "Usuario no encontrado, Id incorrecto";
                result.status = 400;
            }
           
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Transactional
    @Override
    public Result Update(Usuario usuario)
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getIdUsuario());
            
            if(usuarioBD != null)
            {
                usuarioBD.setStatus(usuario.getStatus());
                usuarioBD.setImagen(usuario.getImagen());
                usuarioBD.setUsername(usuario.getUsername());
                usuarioBD.setNombre(usuario.getNombre());
                usuarioBD.setApellidoPaterno(usuario.getApellidoPaterno());
                usuarioBD.setApellidoMaterno(usuario.getApellidoMaterno());
                usuarioBD.setFechaNacimiento(usuario.getFechaNacimiento());
                usuarioBD.setSexo(usuario.getSexo());
                usuarioBD.setCurp(usuario.getCurp());
                usuarioBD.setEmail(usuario.getEmail());
                usuarioBD.setPassword(usuario.getPassword());
                usuarioBD.setTelefono(usuario.getTelefono());
                usuarioBD.setCelular(usuario.getCelular());
                usuarioBD.Rol = usuario.Rol;
                
                entityManager.merge(usuarioBD);
                result.correct = true;
                result.object = usuarioBD;
                result.status = 200;
            } else
            {
                result.errorMessage = "Usuario no encontrado, Id incorrecto";
                result.status = 400;
            }    
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }       
        return result;
    }
    
    @Transactional
    @Override
    public Result UpdateStatus(Usuario usuario)
    {
        Result result = new Result();
        
        try 
        {
            Usuario usuarioBD = entityManager.find(Usuario.class, usuario.getIdUsuario());
            
            if(usuarioBD != null)
            {
                usuarioBD.setStatus(usuario.getStatus());
                entityManager.merge(usuarioBD);
                
                result.object = usuarioBD;
                result.correct = true;
                result.status = 200;
            } else
            {
                result.errorMessage = "Usuario no encontrado, Id incorrecto";
                result.status = 400;
            }
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    private static String rutaDestino = "/src/main/resources/archivos/";
    private static String logFile = "C:\\Users\\digis\\OneDrive\\Documentos\\ITZEL HERNANDEZ LUNA\\05 de Agosto 2025 - Vistas HTML con Stored Procedures y Spring\\com.digis01_IHernandezProgramacionNCapas_jar_0.0.1-SNAPSHOTService\\src\\main\\resources\\archivos\\LogCargaMasiva.txt";
    private static long expiraMS = 120_000L;
    private static SimpleDateFormat logDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public Result CargarArchivo(MultipartFile file) 
    {
        Result result = new Result();
        
        try 
        {
            String root = System.getProperty("user.dir");
            String nombreArchivo = file.getOriginalFilename();
            String extension = GetExtension(nombreArchivo);
            
            String rutaOriginal = root + rutaDestino + nombreArchivo;
            // Guarda y genera la ruta cifrada
            String rutaCifrada = Sha1(rutaOriginal);
            
            // Guarda el archivo en la carpeta de destino
            Path destino = Paths.get(rutaOriginal);
            file.transferTo(destino); //Guarda el archivo en la ruta "/src/main/resources/archivos/"
            
            // Valida que el log no esté duplicado
            String validacion = ValidarLog(rutaCifrada, nombreArchivo);
            
            // Expiracion del archivo si pasan más de 2 minutos
            if(TiempoExpiracion(rutaCifrada))
            {
                GuardarLog(System.lineSeparator() + rutaCifrada, nombreArchivo, EstadoArchivo.Error.ordinal(), "Expirado por inactividad (>2 min)");
                result.correct = false;
                result.errorMessage = "El archivo cargado expiró por inactividad, súbalo nuevamente";
                result.status = 408;
            }
            
            // Valida el formato del archivo (si es .txt o .xlsx)
            List<Usuario> usuarios = extension.equalsIgnoreCase("txt") ? ProcesarTXT(destino.toFile()) : ProcesarExcel(destino.toFile());
            
            if(usuarios == null || usuarios.isEmpty())
            {
                GuardarLog(rutaCifrada, nombreArchivo, EstadoArchivo.Error.ordinal(), "Archivo vacío o ilegible.");
                result.status = 400;
                return result;
            }
            // Si todo es correcto actualiza el LogCargaMasiva.txt
            GuardarLog(rutaCifrada, nombreArchivo, EstadoArchivo.Procesar.ordinal(), "");
            result.correct = true;
            result.status = 200;
            result.errorMessage = "Archivo válido, listo para procesar.";
            result.object = Map.of("rutaCifrada", rutaCifrada, "nombreArchivo", nombreArchivo, "estadoArchivo", EstadoArchivo.Procesar.ordinal());
            return result;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return result;
    }

    @Transactional
    @Override
    public Result ProcesarArchivo(String nombreArchivo) 
    {
        Result result = new Result();
        
        try 
        {        
            String root = System.getProperty("user.dir");
            String extension = GetExtension(nombreArchivo);
            
            String rutaOriginal = root + rutaDestino + nombreArchivo;
            // Guarda y genera la ruta cifrada
            String rutaCifrada = Sha1(rutaOriginal);
            
            // Comprueba que el archivo existe en el servidor
            Path destino = Paths.get(rutaOriginal);
            if(!Files.exists(destino))
            {
                result.status = 404;
                result.errorMessage = "El archivo no existe en el servidor.";
                return result;
            }
            
            List<Usuario> usuarios = extension.equalsIgnoreCase("txt") ? ProcesarTXT(destino.toFile()) : ProcesarExcel(destino.toFile());
            
            if (usuarios == null || usuarios.isEmpty()) 
            {
                GuardarLog(rutaCifrada, nombreArchivo, EstadoArchivo.Error.ordinal(), "No se pudieron leer los datos del archivo.");
                result.status = 400;
                return result;
            }
            
            List<ErrorCM> errores = ValidarDatos(usuarios);
            if (!errores.isEmpty()) 
            {
                GuardarLog(rutaCifrada, nombreArchivo, EstadoArchivo.Error.ordinal(), "Errores en datos: " + errores.size());
                result.correct = false;
                result.status = 400;
                result.errorMessage = "Errores en validaciones.";
                result.object = errores;
                return result;
            }
            
            // Inserta los campos del archivo en la BD
            for (Usuario usuario : usuarios) 
            {
                Add(usuario);
            }
            
            // Marca el Log como PROCESADO (Status 2)
            GuardarLog(rutaCifrada, nombreArchivo, EstadoArchivo.Procesado.ordinal(), "Archivo procesado con éxito.");
            result.correct = true;
            result.status = 200;
            result.errorMessage = "Archivo insertado a BD con éxito.";
            result.object = usuarios.size();
            return result;
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return result;
    }
    
    private String GetExtension(String nombre)
    {
        int index = nombre.lastIndexOf(".");
        return (index >= 0) ? nombre.substring(index + 1) : "";
    }
    
    private String Sha1(String input)
    {
        try 
        {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] digest = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte bt : digest) 
            {
                stringBuilder.append(String.format("%02x", bt));
                return stringBuilder.toString();
            }
            
        } catch (NoSuchAlgorithmException ex) 
        {
            Logger.getLogger(UsuarioJPADAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private boolean  TiempoExpiracion(String rutaCifrada)
    {
        // Revisa si el log existe
        Path logPath = Paths.get(logFile);
        if (!Files.exists(logPath))
            return true;
        // Lee el log y busca coincidencias para saber si el archivo expiró por tiempo
        try 
        {
            List<String> lineas = Files.readAllLines(logPath, StandardCharsets.UTF_8);
            for (int i = lineas.size() - 1; i >= 0; i--) 
            {
                String[] partes = lineas.get(i).split("\\|", -1);
                if (partes.length >= 5 && partes[0].equals(rutaCifrada) && partes[2].equals(String.valueOf(EstadoArchivo.Procesar.ordinal()))) 
                {
                    try 
                    {
                        Date fecha = logDate.parse(partes[3]);
                        long dif = System.currentTimeMillis() - fecha.getTime();
                        if (dif > expiraMS) 
                        {
                            return true;
                        }
                        return false;
                    } catch (Exception ex) 
                    {
                        // Si no se puede parsear la fecha se considera expirado
                        return true;
                    }
                }
            }
        } catch (IOException ex) 
        {
            Logger.getLogger(UsuarioJPADAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private String ValidarLog(String rutaCifrada, String nombreArchivo)
    {
        // Reglas de validación sobre el Log antes de aceptar un nuevo archivo
        Path logPath = Paths.get(logFile);
        if (!Files.exists(logPath))
            return  "OK";
        
        try(Stream<String> lines = Files.lines(logPath, StandardCharsets.UTF_8))
        {
            List<String[]> registros = lines.map(l -> l.split("\\|")).filter(arr -> arr.length >= 4).collect(Collectors.toList());
            
            // PROCESADO (Si logStatus = 2 & rutaCifrada ya existe -> el archivo ya fue procesado)
            if (registros.stream().anyMatch(r -> r[0].equals(rutaCifrada) && r[2].equals(String.valueOf(EstadoArchivo.Procesado.ordinal()))))
                return "El archivo ya fue procesado.";
            
            // PROCESAR (Si logStatus = 1 & rutaCifrada ya existe -> rechazar: el archivo ya existe)
            if (registros.stream().anyMatch(r -> r[1].equals(nombreArchivo) && r[2].equals(String.valueOf(EstadoArchivo.Procesar.ordinal()))))
                return "El archivo ya existe, cambie el nombre o suba otro archivo.";
            
            //  (Si logStatus = 1 & rutaCifrada no tiene concidencias -> aceptar: el archivo se puede subir)
            if (registros.stream().anyMatch(r -> !r[1].equals(nombreArchivo) && r[2].equals(String.valueOf(EstadoArchivo.Procesar.ordinal()))))
                return "Se puede subir un archivo distinto.";
            
            // ERROR  (Si logStatus = 0 -> rechazar: el archivo contiene errores)
            if (registros.stream().anyMatch(r -> r[0].equals(rutaCifrada) && r[2].equals(String.valueOf(EstadoArchivo.Error.ordinal()))))
                return "El archivo se puede volver a subir.";
        } catch (IOException ex) 
        {
            Logger.getLogger(UsuarioJPADAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "OK";
    }
    
    private void GuardarLog(String rutaCifrada, String nombreArchivo, int estado, String observacion)
    {
        String fecha = logDate.format(new Date().getTime());
        String registro = rutaCifrada + "|" + nombreArchivo + "|" + estado + "|" + fecha + "|"+ observacion + System.lineSeparator();
        
        try 
        {
            Files.write(Paths.get(logFile), registro.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ex) 
        {
            Logger.getLogger(UsuarioJPADAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   private List<Usuario> ProcesarTXT(File file)
   {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

            String linea = "";
            List<Usuario> usuarios = new ArrayList<>();
            while ((linea = bufferedReader.readLine()) != null) 
            {
                String[] campo = linea.split("\\|");
                Usuario usuario = new Usuario();
                Integer status = campo[0] == "" ? null : Integer.parseInt(campo[0]);
                usuario.setStatus(status);
                usuario.setUsername(campo[1]);
                usuario.setNombre(campo[2]);
                usuario.setApellidoPaterno(campo[3]);
                usuario.setApellidoMaterno(campo[4]);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = campo[5] == "" ? null : format.parse(campo[5]);
                usuario.setFechaNacimiento(fecha);
                usuario.setSexo(campo[6]);
                usuario.setCurp(campo[7]);
                usuario.setEmail(campo[8]);
                usuario.setPassword(campo[9]);
                usuario.setTelefono(campo[10]);
                usuario.setCelular(campo[11]);

                usuario.Rol = new Rol();
                Integer idRol = campo[12] == "" ? null : Integer.parseInt(campo[12]);
                usuario.Rol.setIdRol(idRol);

                usuario.Direcciones = new ArrayList<>();
                Direccion direccion = new Direccion();
                direccion.setCalle(campo[13]);
                direccion.setNumeroExterior(campo[14]);
                direccion.setNumeroInterior(campo[15]);

                direccion.Colonia = new Colonia();
                Integer idColonia = campo[16] == "" ? null : Integer.parseInt(campo[16]);
                direccion.Colonia.setIdColonia(idColonia);
                usuario.Direcciones.add(direccion);

                usuarios.add(usuario);
            }
            return usuarios;
        } catch (Exception ex) 
        {
            System.out.println("Error");
            return null;
        }
   }
   
    private List<Usuario> ProcesarExcel(File file) 
    {
        try 
        {
            List<Usuario> usuarios = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) 
            {
                Usuario usuario = new Usuario();
                usuario.setStatus(row.getCell(0) != null ? (int) row.getCell(0).getNumericCellValue() : 0);
                usuario.setUsername(row.getCell(1) != null ? row.getCell(1).toString() : "");
                usuario.setNombre(row.getCell(2) != null ? row.getCell(2).toString() : "");
                usuario.setApellidoPaterno(row.getCell(3) != null ? row.getCell(3).toString() : "");
                usuario.setApellidoMaterno(row.getCell(4) != null ? row.getCell(4).toString() : "");
                SimpleDateFormat format = new SimpleDateFormat();
                if (row.getCell(5) != null) 
                {
                    if (row.getCell(5).getCellType() == CellType.NUMERIC || DateUtil.isCellDateFormatted(row.getCell(5))) 
                    {
                        usuario.setFechaNacimiento(row.getCell(5).getDateCellValue());
                    } else 
                    {
                        usuario.setFechaNacimiento(format.parse(row.getCell(5).toString()));
                    }
                }
                usuario.setSexo(row.getCell(6) != null ? row.getCell(6).toString() : "");
                usuario.setCurp(row.getCell(7) != null ? row.getCell(7).toString() : "");
                usuario.setEmail(row.getCell(8) != null ? row.getCell(8).toString() : "");
                usuario.setPassword(row.getCell(9) != null ? row.getCell(9).toString() : "");
                DataFormatter dataFormatter = new DataFormatter();
                usuario.setTelefono(row.getCell(10) != null ? dataFormatter.formatCellValue(row.getCell(10)) : "");
                usuario.setCelular(row.getCell(11) != null ? dataFormatter.formatCellValue(row.getCell(11)) : "");

                usuario.Rol = new Rol();
                usuario.Rol.setIdRol(row.getCell(12) != null ? (int) row.getCell(12).getNumericCellValue() : 0);

                usuario.Direcciones = new ArrayList<>();
                Direccion direccion = new Direccion();
                direccion.setCalle(row.getCell(13) != null ? row.getCell(13).toString() : "");
                direccion.setNumeroExterior(row.getCell(14) != null ? dataFormatter.formatCellValue(row.getCell(14)) : "");
                direccion.setNumeroInterior(row.getCell(15) != null ? dataFormatter.formatCellValue(row.getCell(15)) : "");

                direccion.Colonia = new Colonia();
                direccion.Colonia.setIdColonia(row.getCell(16) != null ? (int) row.getCell(16).getNumericCellValue() : 0);
                usuario.Direcciones.add(direccion);

                usuarios.add(usuario);
            }
            return usuarios;
        } catch (Exception ex) 
        {
            return null;
        }
    }
    
    private List<ErrorCM> ValidarDatos(List<Usuario> usuarios) 
    {
        List<ErrorCM> errores = new ArrayList<>();
        int linea = 1;
        for (Usuario usuario : usuarios) 
        {
            if (usuario.getStatus() <= 0) 
            {
                errores.add(new ErrorCM(linea, String.valueOf(usuario.getStatus()), "El campo STATUS es obligatorio"));
            }
            if (usuario.getUsername() == null || usuario.getUsername() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.getUsername(), "El campo USERNAME es obligatorio"));
            }
            if (usuario.getNombre() == null || usuario.getNombre() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.getNombre(), "El campo NOMBRE es obligatorio"));
            }
            if (usuario.getApellidoPaterno() == null || usuario.getApellidoPaterno() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.getApellidoPaterno(), "El campo APELLIDO PATERNO es obligatorio"));
            }
            if (usuario.getApellidoMaterno() == null || usuario.getApellidoMaterno() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.getApellidoMaterno(), "El campo APELLIDO MATERNO es obligatorio"));
            }
            if (usuario.getFechaNacimiento() == null || usuario.getFechaNacimiento().equals("")) 
            {
                errores.add(new ErrorCM(linea, "fecha vacia", "El campo FECHA DE NACIMIENTO es obligatorio"));
            }
            if (usuario.getSexo() == null || usuario.getSexo() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.getSexo(), "El campo SEXO es obligatorio"));
            }
            if (usuario.getCurp() == null || usuario.getCurp() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.getCurp(), "El campo CURP es obligatorio"));
            }
            if (usuario.getEmail() == null || usuario.getEmail() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.getEmail(), "El campo EMAIL es obligatorio"));
            }
            if (usuario.getPassword() == null || usuario.getPassword() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.getPassword(), "El campo PASSWORD es obligatorio"));
            }
            if (usuario.getTelefono() == null || usuario.getTelefono() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.getTelefono(), "El campo TELEFONO es obligatorio"));
            }
            if (usuario.getCelular() == null || usuario.getCelular() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.getCelular(), "El campo CELULAR es obligatorio"));
            }
            if (usuario.Rol.getIdRol() <= 0) 
            {
                errores.add(new ErrorCM(linea, String.valueOf(usuario.Rol.getIdRol()), "El campo ID ROL debe ser mayor a cero"));
            }
            if (usuario.Direcciones.get(0).getCalle() == null || usuario.Direcciones.get(0).getCalle() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.getNombre(), "El campo CALLE es obligatorio"));
            }
            if (usuario.Direcciones.get(0).getNumeroExterior() == null || usuario.Direcciones.get(0).getNumeroExterior() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.Direcciones.get(0).getNumeroExterior(), "El campo NÚMERO EXTERIOR es obligatorio"));
            }
            if (usuario.Direcciones.get(0).getNumeroInterior() == null || usuario.Direcciones.get(0).getNumeroInterior() == "") 
            {
                errores.add(new ErrorCM(linea, usuario.Direcciones.get(0).getNumeroInterior(), "El campo NÚMERO INTERIOR es obligatorio"));
            }
            if (usuario.Direcciones.get(0).Colonia.getIdColonia() <= 0) 
            {
                errores.add(new ErrorCM(linea, String.valueOf(usuario.Direcciones.get(0).Colonia.getIdColonia()), "El campo ID COLONIA debe ser mayor a cero"));
            }
            linea++;
        }
        return errores;
    }
    
    public enum EstadoArchivo
   {
       Error,
       Procesar,
       Procesado
   }
}
