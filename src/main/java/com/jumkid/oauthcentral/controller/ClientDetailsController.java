package com.jumkid.oauthcentral.controller;

import com.jumkid.oauthcentral.controller.dto.ClientDetails;
import com.jumkid.oauthcentral.controller.response.CommonResponse;
import com.jumkid.oauthcentral.service.ClientDetailsMaintainService;
import com.jumkid.oauthcentral.utils.ClientDetailsField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client-details")
public class ClientDetailsController {

    private final ClientDetailsMaintainService clientDetailsMaintainService;

    @Autowired
    public ClientDetailsController(ClientDetailsMaintainService clientDetailsMaintainService) {
        this.clientDetailsMaintainService = clientDetailsMaintainService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDetails> getAll() {
        return clientDetailsMaintainService.getAll();
    }

    @GetMapping(value = "{clientDetailsId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDetails get(@PathVariable Integer clientDetailsId) {
        return clientDetailsMaintainService.getClientDetails(clientDetailsId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ClientDetails add(@NotNull @Valid @RequestBody ClientDetails clientDetails){
        return clientDetailsMaintainService.saveClientDetails(clientDetails);
    }

    @PutMapping(value = "/{clientDetailsId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDetails update(@PathVariable Integer clientDetailsId,
                                @NotNull @Valid @RequestBody ClientDetails clientDetails) {
        return clientDetailsMaintainService.updateClientDetails(clientDetailsId, clientDetails);
    }

    @PatchMapping(value = "/{clientDetailsId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDetails patch(@PathVariable Integer clientDetailsId,
                               @NotNull @RequestBody Map<String, Object> updatesMap) {
        return clientDetailsMaintainService.patchClientDetails(clientDetailsId, updatesMap);
    }

    @DeleteMapping(value = "{clientDetailsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CommonResponse delete(@PathVariable Integer clientDetailsId) {
        clientDetailsMaintainService.deleteClientDetails(clientDetailsId);
        return new CommonResponse("client details is deleted");
    }

}
