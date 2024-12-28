package br.com.agendaprof.usuario.mapper;


import br.com.agendaprof.usuario.entity.EnumRoles;
import br.com.agendaprof.usuario.entity.Senha;
import br.com.agendaprof.usuario.entity.Usuario;
import br.com.agendaprof.usuario.persistence.entity.UsuarioData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {UsuarioMapper.class})
class UsuarioMapperTest {

    @Autowired
    private UsuarioMapper mapper;

    private Usuario domain() {

        Senha senha = new Senha("123@456");

        Usuario usuario = new Usuario();
        usuario.setId(new Random().nextLong());
        usuario.setCreatedAt(new Date());
        usuario.setUpdatedAt(new Date());
        usuario.setUltimoLogin(new Date());
        usuario.setLogin(UUID.randomUUID().toString());
        usuario.setNome(UUID.randomUUID().toString());
        usuario.setSenha(senha);
        usuario.setPermissoes(Set.of(EnumRoles.ADMIN));

        return usuario;
    }

    private UsuarioData data() {

        UsuarioData usuario = new UsuarioData();
        usuario.setId(new Random().nextLong());
        usuario.setCreatedAt(new Date());
        usuario.setUpdatedAt(new Date());
        usuario.setUltimoLogin(new Date());
        usuario.setLogin(UUID.randomUUID().toString());
        usuario.setNome(UUID.randomUUID().toString());
        usuario.setSenha("123@456");
        usuario.setPermissoes(Set.of(EnumRoles.ADMIN));

        return usuario;
    }

    @Test
    void dadoQueTenhoUmDomain_QuandoChamarToData_deveRetornarUmData(){
        Usuario domain = domain();

        UsuarioData data = mapper.toData(domain);

        assertEquals(domain.getId(), data.getId());
        assertEquals(domain.getLogin(), data.getLogin());
        assertEquals(domain.getNome(), data.getNome());
        assertEquals(domain.getPermissoes(), data.getPermissoes());
        assertEquals(domain.getCreatedAt(), data.getCreatedAt());
        assertEquals(domain.getUpdatedAt(), data.getUpdatedAt());
        assertEquals(domain.getUltimoLogin(), data.getUltimoLogin());
        assertNotNull(data.getSenha());
        assertEquals(domain.getSenha().getValor(), data.getSenha());

    }

    @Test
    void dadoQueTenhoUmaListaDeDomains_QuandoChamarToData_deveRetornarLista(){
        Usuario domain1 = domain();
        Usuario domain2 = domain();
        Usuario domain3 = domain();

        List<UsuarioData> datas = mapper.toDatas(List.of(domain1, domain2, domain3));

        assertEquals(datas.size(), 3);
        assertEquals(datas.get(0).getId(), domain1.getId());
        assertEquals(datas.get(1).getId(), domain2.getId());
        assertEquals(datas.get(2).getId(), domain3.getId());

        assertEquals(datas.get(0).getLogin(), domain1.getLogin());
        assertEquals(datas.get(1).getLogin(), domain2.getLogin());
        assertEquals(datas.get(2).getLogin(), domain3.getLogin());

        assertEquals(datas.get(0).getNome(), domain1.getNome());
        assertEquals(datas.get(1).getNome(), domain2.getNome());
        assertEquals(datas.get(2).getNome(), domain3.getNome());

        assertEquals(datas.get(0).getPermissoes(), domain1.getPermissoes());
        assertEquals(datas.get(1).getPermissoes(), domain2.getPermissoes());
        assertEquals(datas.get(2).getPermissoes(), domain3.getPermissoes());

        assertEquals(datas.get(0).getCreatedAt(), domain1.getCreatedAt());
        assertEquals(datas.get(1).getCreatedAt(), domain2.getCreatedAt());
        assertEquals(datas.get(2).getCreatedAt(), domain3.getCreatedAt());

        assertEquals(datas.get(0).getUpdatedAt(), domain1.getUpdatedAt());
        assertEquals(datas.get(1).getUpdatedAt(), domain2.getUpdatedAt());
        assertEquals(datas.get(2).getUpdatedAt(), domain3.getUpdatedAt());

        assertEquals(datas.get(0).getUltimoLogin(), domain1.getUltimoLogin());
        assertEquals(datas.get(1).getUltimoLogin(), domain2.getUltimoLogin());
        assertEquals(datas.get(2).getUltimoLogin(), domain3.getUltimoLogin());

        assertNotNull(datas.get(0).getSenha());
        assertNotNull(datas.get(1).getSenha());
        assertNotNull(datas.get(2).getSenha());

        assertEquals(datas.get(0).getSenha(), domain1.getSenha().getValor());
        assertEquals(datas.get(1).getSenha(), domain2.getSenha().getValor());
        assertEquals(datas.get(2).getSenha(), domain3.getSenha().getValor());

    }

    @Test
    void dadoQueTenhoUmDomainNull_QuandoChamarToData_deveRetornarUmDataNull(){
        UsuarioData data = mapper.toData(null);
        assertNull(data);
    }

    @Test
    void dadoQueTenhoUmData_QuandoChamarToDomain_deveRetornarUmDomain(){
        UsuarioData data = data();

        Usuario domain = mapper.toDomain(data);

        assertEquals(domain.getId(), data.getId());
        assertEquals(domain.getLogin(), data.getLogin());
        assertEquals(domain.getNome(), data.getNome());
        assertEquals(domain.getPermissoes(), data.getPermissoes());
        assertEquals(domain.getCreatedAt(), data.getCreatedAt());
        assertEquals(domain.getUpdatedAt(), data.getUpdatedAt());
        assertEquals(domain.getUltimoLogin(), data.getUltimoLogin());

    }

    @Test
    void dadoQueTenhoUmaListaDeDatas_QuandoChamarToData_deveRetornarLista(){
        UsuarioData data1 = data();
        UsuarioData data2 = data();
        UsuarioData data3 = data();

        List<Usuario> domains = mapper.toDomains(List.of(data1, data2, data3));

        assertEquals(domains.size(), 3);
        assertEquals(domains.get(0).getId(), data1.getId());
        assertEquals(domains.get(1).getId(), data2.getId());
        assertEquals(domains.get(2).getId(), data3.getId());

        assertEquals(domains.get(0).getLogin(), data1.getLogin());
        assertEquals(domains.get(1).getLogin(), data2.getLogin());
        assertEquals(domains.get(2).getLogin(), data3.getLogin());

        assertEquals(domains.get(0).getNome(), data1.getNome());
        assertEquals(domains.get(1).getNome(), data2.getNome());
        assertEquals(domains.get(2).getNome(), data3.getNome());

        assertEquals(domains.get(0).getPermissoes(), data1.getPermissoes());
        assertEquals(domains.get(1).getPermissoes(), data2.getPermissoes());
        assertEquals(domains.get(2).getPermissoes(), data3.getPermissoes());

        assertEquals(domains.get(0).getCreatedAt(), data1.getCreatedAt());
        assertEquals(domains.get(1).getCreatedAt(), data2.getCreatedAt());
        assertEquals(domains.get(2).getCreatedAt(), data3.getCreatedAt());

        assertEquals(domains.get(0).getUpdatedAt(), data1.getUpdatedAt());
        assertEquals(domains.get(1).getUpdatedAt(), data2.getUpdatedAt());
        assertEquals(domains.get(2).getUpdatedAt(), data3.getUpdatedAt());

        assertEquals(domains.get(0).getUltimoLogin(), data1.getUltimoLogin());
        assertEquals(domains.get(1).getUltimoLogin(), data2.getUltimoLogin());
        assertEquals(domains.get(2).getUltimoLogin(), data3.getUltimoLogin());

    }

    @Test
    void dadoQueTenhoUmDataNull_QuandoChamarToDomain_deveRetornarUmDomainNull(){
        Usuario domain = mapper.toDomain(null);
        assertNull(domain);
    }
}
