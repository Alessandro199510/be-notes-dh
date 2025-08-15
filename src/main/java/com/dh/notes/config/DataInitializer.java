package com.dh.notes.config;

import com.dh.notes.repository.NoteRepository;
import com.dh.notes.repository.TagRepository;
import com.dh.notes.repository.UserRepository;
import com.dh.notes.model.Note;
import com.dh.notes.model.Tag;
import com.dh.notes.model.User;
import com.dh.notes.util.enums.NoteStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initSingleUser(UserRepository userRepository, NoteRepository noteRepository, TagRepository tagRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("ales").isEmpty()) {

                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@admin.com");
                admin.setPassword(passwordEncoder.encode("password"));
                admin.setRole("ADMIN");
                admin = userRepository.save(admin);

                User user = new User();
                user.setUsername("ales");
                user.setEmail("ales@email.com");
                user.setPassword(passwordEncoder.encode("password"));
                user.setRole("USER");
                user = userRepository.save(user);

                Tag trabajoTag = new Tag();
                trabajoTag.setName("Trabajo");
                trabajoTag.setUserId(user.getId());

                Tag personalTag = new Tag();
                personalTag.setName("Personal");
                personalTag.setUserId(user.getId());

                Tag ideasTag = new Tag();
                ideasTag.setName("Ideas");
                ideasTag.setUserId(user.getId());

                Tag comprasTag = new Tag();
                comprasTag.setName("Compras");
                comprasTag.setUserId(user.getId());

                tagRepository.saveAll(Arrays.asList(trabajoTag, personalTag, ideasTag, comprasTag));

                createNote("Preparar presentación", "Revisar slides y datos para la reunión de mañana.", user, new HashSet<>(Arrays.asList(trabajoTag)), noteRepository);
                createNote("Lista de la compra", "Leche, pan, huevos, aceite.", user, new HashSet<>(Arrays.asList(comprasTag, personalTag)), noteRepository);
                createNote("Nueva idea de proyecto", "Una aplicación para gestionar finanzas personales con IA.", user, new HashSet<>(Arrays.asList(ideasTag, trabajoTag)), noteRepository);
                createNote("Revisar presupuesto", "Analizar los gastos del mes y hacer ajustes.", user, new HashSet<>(Arrays.asList(personalTag)), noteRepository);
                createNote("Llamar al médico", "Agendar una cita para el chequeo anual.", user, new HashSet<>(Arrays.asList(personalTag)), noteRepository);
                createNote("Actualizar CV", "Añadir el último proyecto al currículum.", user, new HashSet<>(Arrays.asList(trabajoTag)), noteRepository);
                createNote("Planear vacaciones", "Buscar destinos y vuelos para el verano.", user, new HashSet<>(Arrays.asList(personalTag)), noteRepository);
                createNote("Comprar regalo", "Regalo de cumpleaños para mi hermano.", user, new HashSet<>(Arrays.asList(comprasTag)), noteRepository);
                createNote("Leer libro de programación", "Capítulo 5: Patrones de diseño.", user, new HashSet<>(Arrays.asList(ideasTag)), noteRepository);
                createNote("Tarea del hogar", "Limpiar la cocina y el baño.", user, new HashSet<>(Arrays.asList(personalTag)), noteRepository);
                createNote("Entrenamiento", "Plan de ejercicios para esta semana.", user, new HashSet<>(Arrays.asList(personalTag)), noteRepository);
                createNote("Reunión de equipo", "Discutir los avances y próximos pasos.", user, new HashSet<>(Arrays.asList(trabajoTag)), noteRepository);
                createNote("Pago de facturas", "Pagar luz, agua e internet.", user, new HashSet<>(Arrays.asList(personalTag, comprasTag)), noteRepository);
                createNote("Buscar cursos online", "Investigar sobre cursos de Spring Boot avanzados.", user, new HashSet<>(Arrays.asList(trabajoTag, ideasTag)), noteRepository);
                createNote("Recordatorio", "Enviar el informe de gastos antes de fin de mes.", user, new HashSet<>(Arrays.asList(trabajoTag)), noteRepository);
            }
        };
    }

    private void createNote(String title, String content, User user, Set<Tag> tags, NoteRepository noteRepository) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(user);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        note.setStatus(NoteStatus.ACTIVE);
        note.setTags(tags);
        noteRepository.save(note);
    }
}