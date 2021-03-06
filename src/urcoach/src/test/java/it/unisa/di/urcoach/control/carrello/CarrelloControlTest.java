package it.unisa.di.urcoach.control.carrello;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisa.di.urcoach.control.MainControl;
import it.unisa.di.urcoach.model.entity.Acquisto;
import it.unisa.di.urcoach.model.entity.Atleta;
import it.unisa.di.urcoach.model.entity.Carrello;
import it.unisa.di.urcoach.model.entity.Pacchetto;
import it.unisa.di.urcoach.model.repository.PacchettoRepository;
import it.unisa.di.urcoach.model.repository.PersonalTrainerRepository;
import it.unisa.di.urcoach.model.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.servlet.FlashMap;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CarrelloControl.class)
@AutoConfigureMockMvc(addFilters = false)
class CarrelloControlTest {

    @MockBean
    PacchettoService pacchettoService;
    @MockBean
    CategoriaService categoriaService;
    @MockBean
    PersonalTrainerService personalTrainerService;
    @MockBean
    FatturaService fatturaService;
    @MockBean
    AcquistoService acquistoService;
    @MockBean
    AtletaService atletaService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void aggiungiCarrello_CreaAggiungi() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        when(pacchettoService.findById(1)).thenReturn(p1);
        MockHttpServletRequest request = mockMvc.perform(post("/carrello")
                .param("idPacchetto", "1")
                .param("azione", "aggiungi"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/carrello"))
                .andExpect(model().attributeExists("pacchetti", "trainer", "atleta"))
                .andReturn().getRequest();
        Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
        assertTrue(carrello.getPacchetti().contains(p1));
    }

    @Test
    void aggiungiCarrello_CarrelloAggiungi() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        when(pacchettoService.findById(1)).thenReturn(p1);
        MockHttpServletRequest request = mockMvc.perform(post("/carrello")
                .param("idPacchetto", "1")
                .param("azione", "aggiungi")
                .sessionAttr("carrello", new Carrello()))
                .andExpect(status().isOk())
                .andExpect(view().name("View/carrello"))
                .andExpect(model().attributeExists("pacchetti", "trainer", "atleta"))
                .andReturn().getRequest();
        Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
        assertTrue(carrello.getPacchetti().contains(p1));
    }

    @Test
    void aggiungiCarrello_CarrelloRimuovi() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        when(pacchettoService.findById(1)).thenReturn(p1);
        Carrello carrello = new Carrello();
        carrello.put(p1);
        MockHttpServletRequest request = mockMvc.perform(post("/carrello")
                .param("idPacchetto", "1")
                .param("azione", "rimuovi")
                .sessionAttr("carrello", carrello))
                .andExpect(status().isOk())
                .andExpect(view().name("View/carrello"))
                .andExpect(model().attributeExists("pacchetti", "trainer", "atleta"))
                .andReturn().getRequest();
        carrello = (Carrello) request.getSession().getAttribute("carrello");
        assertEquals(carrello.getPacchetti().size(), 0);
    }

    @Test
    void aggiungiCarrello_CreaRimuovi() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        when(pacchettoService.findById(1)).thenReturn(p1);
        MockHttpServletRequest request = mockMvc.perform(post("/carrello")
                .param("idPacchetto", "1")
                .param("azione", "rimuovi"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/carrello"))
                .andExpect(model().attributeExists("pacchetti", "trainer", "atleta"))
                .andReturn().getRequest();
        Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
        assertFalse(carrello.getPacchetti().contains(p1));
    }

    @Test
    void aggiungiCarrello_Crea() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        when(pacchettoService.findById(1)).thenReturn(p1);
        MockHttpServletRequest request = mockMvc.perform(post("/carrello")
                .param("idPacchetto", "1")
                .param("azione", "ciao"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/carrello"))
                .andExpect(model().attributeExists("pacchetti", "trainer", "atleta"))
                .andReturn().getRequest();
        Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
        assertFalse(carrello.getPacchetti().contains(p1));
    }

    @Test
    void mostraCarrello_Crea() throws Exception{
        mockMvc.perform(get("/carrello"))
                .andExpect(status().isOk())
                .andExpect(view().name("View/carrello"))
                .andExpect(model().attributeExists("pacchetti", "trainer", "atleta"));
    }

    @Test
    void mostraCarrello() throws Exception{
        mockMvc.perform(get("/carrello")
                .sessionAttr("carrello", new Carrello()))
                .andExpect(status().isOk())
                .andExpect(view().name("View/carrello"))
                .andExpect(model().attributeExists("pacchetti", "trainer", "atleta"));
    }

    @Test
    void checkout_NoAtleta() throws Exception{
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        Carrello carrello = new Carrello();
        carrello.put(p1);
        mockMvc.perform(get("/checkout")
                .sessionAttr("carrello", carrello))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/carrello#login"));
    }

    @Test
    void checkout() throws Exception{
        Atleta a = new Atleta();
        a.setEmail("salviofasano@gmail.com");
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        Carrello carrello = new Carrello();
        carrello.put(p1);
        mockMvc.perform(get("/checkout")
                .sessionAttr("atleta", a)
                .sessionAttr("carrello", carrello))
                .andExpect(status().isOk())
                .andExpect(view().name("View/checkout"))
                .andExpect(model().attributeExists("pacchetti", "trainer", "atleta"));
    }

    @Test
    void nuovaFattura_AllFieldFail() throws Exception{
        Atleta a = new Atleta();
        a.setEmail("salviofasano@gmail.com");
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        p1.setCosto(100);
        Pacchetto p2 = new Pacchetto();
        p1.setIdPacchetto(2);
        p1.setNome("Massa in 100 giorni");
        p1.setCosto(100);
        Carrello carrello = new Carrello();
        carrello.put(p1);
        carrello.put(p2);
        List<String> errori = new ArrayList<>();
        errori.add("Il numero della carta di credito non rispetta il formato");
        errori.add("La data di scadenza non rispetta il formato");
        errori.add("Il cvc della carta di credito non rispetta il formato");
        mockMvc.perform(post("/nuovaFattura")
                .param("costo", "200")
                .param("atleta", "salviofasano@gmail.com")
                .param("cc", "")
                .param("dataScadenza", "")
                .param("cvc", "")
                .sessionAttr("carrello", carrello))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/checkout"))
                .andExpect(flash().attribute("errori", errori));
        verify(acquistoService, times(0)).save(any(Acquisto.class));
    }

    @Test
    void nuovaFattura_Correct() throws Exception{
        Atleta a = new Atleta();
        a.setEmail("salviofasano@gmail.com");
        Pacchetto p1 = new Pacchetto();
        p1.setIdPacchetto(1);
        p1.setNome("Dimagrire in 100 giorni");
        p1.setCosto(100);
        Pacchetto p2 = new Pacchetto();
        p1.setIdPacchetto(2);
        p1.setNome("Massa in 100 giorni");
        p1.setCosto(100);
        Carrello carrello = new Carrello();
        carrello.put(p1);
        carrello.put(p2);
        mockMvc.perform(post("/nuovaFattura")
                .param("costo", "200")
                .param("atleta", "salviofasano@gmail.com")
                .param("cc", "5333171030866194")
                .param("dataScadenza", "01/43")
                .param("cvc", "123")
                .sessionAttr("carrello", carrello))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/areaPersonale"))
                .andExpect(flash().attributeCount(0));
        verify(acquistoService, times(2)).save(any(Acquisto.class));
    }
}