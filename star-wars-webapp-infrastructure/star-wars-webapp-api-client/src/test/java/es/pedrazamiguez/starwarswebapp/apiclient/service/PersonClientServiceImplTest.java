package es.pedrazamiguez.starwarswebapp.apiclient.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PersonClientServiceImplTest {

  @InjectMocks private PersonClientServiceImpl personClientServiceImpl;
}
