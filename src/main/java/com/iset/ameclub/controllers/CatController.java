package com.iset.ameclub.controllers;

import com.iset.ameclub.dao.RoleRepository;
import com.iset.ameclub.dao.UserRepository;
import com.iset.ameclub.entities.*;
import com.iset.ameclub.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@Controller
public class CatController {
    @Autowired
    ClubService clubService;
    @Autowired
    UserRepository userService;
    @Autowired
    DemandeMembreService demandeMembreService;
    @Autowired
    DemandeClubService demandeClubService;
    @Autowired
    ActiviteService activiteService;
    @Autowired
    DemandeActiviteService demandeActiviteService;
    @Autowired
    DemandeAjoutActiviteService demandeAjoutActiviteService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserServiceImpl userServiceImp;
    private PasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        @PreAuthorize("hasRole('VISITEUR') || hasRole('MEMBRE') || hasRole('PRESIDENT')|| hasRole('ADMIN')")
    @RequestMapping("/ListeClub")
    public String listeClub(@AuthenticationPrincipal UserDetails userDetails,
            ModelMap modelMap,@RequestParam(name = "nom", defaultValue = "") String nom,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size)
    {String username = userDetails.getUsername();
        User user = userService.findUsername(username);
        Page<Club> cl = clubService.getAllClubsParPage(nom,page, size);
        modelMap.addAttribute("clubs", cl);
        modelMap.addAttribute("pages", new int[cl.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("nom", nom);
        for (Club club: cl) {
            String demandeStatus = demandeMembreService.getDemandeStatut(user.getUserId(), club.getClubId());
            club.setDemandeStatus(demandeStatus);
        }

        return "listeClub";
    }
    @PreAuthorize("hasRole('VISITEUR') || hasRole('MEMBRE') || hasRole('PRESIDENT')")
    @PostMapping("/saveDemandeMembre")
    public String saveDemandeMembre(@RequestParam("clubId") Long clubId,@RequestParam("userId") Long userId,
                                    ModelMap modelMap) throws ParseException {
        User user=userService.getById(userId);
        Club club=clubService.getClub(clubId);
        DemandeMembreId demandeMembreId=new DemandeMembreId(user,club);
        DemandeMembre demandeMembre=new DemandeMembre(demandeMembreId,"en attente");
        demandeMembreService.saveDemandeMembre(demandeMembre);
        return "redirect:/ListeClub";
    }
    @PreAuthorize("hasRole('VISITEUR') || hasRole('MEMBRE') || hasRole('PRESIDENT')")
    @PostMapping("/annulerDemandeMembre")
    public String annulerDemandeMembre(@RequestParam("userId") Long userId,@RequestParam("clubId") Long clubId,
                                         ModelMap modelMap) throws ParseException {
        User user=userService.getById(userId);
        Club club=clubService.getClub(clubId);
        DemandeMembreId demandeMembreId=new DemandeMembreId(user,club);
        DemandeMembre demandeMembre=demandeMembreService.getDemandeMembre(demandeMembreId);
        demandeMembre.setStatus("annulé");
        demandeMembreService.saveDemandeMembre(demandeMembre);
        return "redirect:/ListeClub";
    }
    @RequestMapping("/")
    public String show() {
        return "index";
    }
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "signUp";
    }
    @PostMapping("/signup")
    public String processSignupForm(@ModelAttribute("userForm") UserForm userForm,Model model) {
        User appUser = new User();String msg;
        User user=userService.findUsername(userForm.getUsername());
        if (user!=null)
             msg="User already exists";
        else{
        if (!userForm.getPassword().equals(userForm.getConfirmedPassword()))
             msg="Please confirm your password";
        else {
        appUser.setUsername(userForm.getUsername());
        appUser.setNom(userForm.getNom());
        appUser.setPrenom(userForm.getPrenom());
        appUser.setPrenom(userForm.getPrenom());
        appUser.setTel(userForm.getTel());
        appUser.setEmail(userForm.getEmail());
        Set<Role> roles = new HashSet<Role>();
        Role r = roleRepository.findByName("ROLE_VISITEUR");
        appUser.getRoles().add(r);
        appUser.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        userService.save(appUser);
        msg="user créé avec succés";
        }}
        model.addAttribute("msg",msg);
        return "signUp";

    }

    @PreAuthorize("hasRole('VISITEUR') || hasRole('MEMBRE') || hasRole('PRESIDENT')")
    @RequestMapping("/showDemandeClub")
    public String showDemandeClub(ModelMap modelMap)
    {
        modelMap.addAttribute("demande", new DemandeClub());
        return "createClub";
    }
    @PreAuthorize("hasRole('VISITEUR') || hasRole('MEMBRE') || hasRole('PRESIDENT')")
    @RequestMapping("/saveDemandeClub")
    public String saveDemandeClub(@RequestParam("nomClub") String nomClub, @RequestParam("nbMembre") Integer nbMembre, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateCreation, @RequestParam("idPresident") Long idPresident,
                                  ModelMap modelMap) throws ParseException {
        User user=userService.getById(idPresident);
        DemandeClub demandeClub=new DemandeClub(nomClub,nbMembre,dateCreation,user,"en attente");
        DemandeClub saveDemandeClub = demandeClubService.saveDemandeClub(demandeClub);
        String msg = "DemandeClub envoyé avec succées ";
        modelMap.addAttribute("msg", msg);
        return "createClub";
    }
    @PreAuthorize("hasRole('MEMBRE') || hasRole('PRESIDENT')")
    @RequestMapping("/ListeActivite")
    public String listeActivite(@AuthenticationPrincipal UserDetails userDetails,
            ModelMap modelMap,@RequestParam(name = "nom", defaultValue = "") String nom,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size)
    {
        String username = userDetails.getUsername();
        User user = userService.findUsername(username);
        Page<Activite> ac = activiteService.getAllActiviteParPage(nom,page, size);
        modelMap.addAttribute("activites", ac);
        modelMap.addAttribute("pages", new int[ac.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("nom", nom);
        for (Activite activite : ac) {
            String demandeStatus = demandeActiviteService.getDemandeStatut(user.getUserId(), activite.getActiviteId());
            activite.setDemandeStatus(demandeStatus);
        }
        return "listeActivite";
    }
    @PreAuthorize("hasRole('MEMBRE') || hasRole('PRESIDENT')")
    @PostMapping("/saveDemandeActivite")
    public String saveDemandeActivite(@RequestParam("userId") Long userId,@RequestParam("activiteId") Long activiteId,
                                    ModelMap modelMap) throws ParseException {
        User user=userService.getById(userId);
        Activite activite=activiteService.getActivite(activiteId);
        DemandeActiviteId demandeActiviteId=new DemandeActiviteId(user,activite);
        DemandeActivite demandeActivite=new DemandeActivite(demandeActiviteId,"en attente");
        demandeActiviteService.saveDemandeActivite(demandeActivite);
        String msg = "Demande envoyée avec succès ";
        return "redirect:/ListeActivite";
    }
    @PreAuthorize(" hasRole('MEMBRE') || hasRole('PRESIDENT')")
    @PostMapping("/annulerDemandeActivite")
    public String annulerDemandeActivite(@RequestParam("userId") Long userId,@RequestParam("activiteId") Long activiteId,
                                      ModelMap modelMap) throws ParseException {
        User user=userService.getById(userId);
        Activite activite=activiteService.getActivite(activiteId);
        DemandeActiviteId demandeActiviteId=new DemandeActiviteId(user,activite);
        DemandeActivite demandeActivite=demandeActiviteService.getDemandeActivite(demandeActiviteId);
        demandeActivite.setStatus("annulé");
        demandeActiviteService.saveDemandeActivite(demandeActivite);
        return "redirect:/ListeActivite";
    }
    @PreAuthorize("hasRole('MEMBRE')")
    @RequestMapping("/showAjouterActivite")
    public String showAjouterActivite(@AuthenticationPrincipal UserDetails userDetails, ModelMap modelMap)
    {   String username = userDetails.getUsername();
        User user = userService.findUsername(username);
        modelMap.addAttribute("user", user);
        List<Club> clubs = new ArrayList<>(user.getClubs());
        modelMap.addAttribute("clubs", clubs);
        return "ajouterActivite";
    }
    @PreAuthorize("hasRole('MEMBRE') ")
    @RequestMapping("/addActivite")
    public String addActivite(@RequestParam("nomActivite") String nomActivite,@RequestParam("lieu") String lieu,@RequestParam("dateActivite") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateActivite,
                                  @RequestParam("prixActivite") Float prixActivite,@RequestParam("sujet") String sujet,@RequestParam("clubId") Long clubId,ModelMap modelMap) throws ParseException{
    Club c=clubService.getClub(clubId);
    DemandeAjoutActivite demande=new DemandeAjoutActivite(nomActivite,lieu,dateActivite,prixActivite,sujet,c,"en attente");
    demandeAjoutActiviteService.saveDemandeAjoutActivite(demande);
    String msg = "Demande activite envoyé avec succées ";
    modelMap.addAttribute("msg", msg);
    return "redirect:/showAjouterActivite";
    }
    @PreAuthorize(" hasRole('PRESIDENT')")
    @RequestMapping("/ListDemandeMembre")
    public String listeDemandeMembre( @AuthenticationPrincipal UserDetails userDetails,
            ModelMap modelMap,@RequestParam(name = "nom", defaultValue = "") String nom,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size)

    {
        Page<DemandeMembre> ac = demandeMembreService.getAllDemandeMembreParPage(nom,page, size);
        String username = userDetails.getUsername();
        User user = userService.findUsername(username);
        modelMap.addAttribute("demandeMembre", ac);
        modelMap.addAttribute("users", user);
        List<Club> clubs = clubService.getAllClubs();
        modelMap.addAttribute("clubs", clubs);
        modelMap.addAttribute("pages", new int[ac.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "listeDemandeMembre";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/accepterDemandeMembre")
    public String accepterDemandeMembre(@ModelAttribute("demandeId")DemandeMembreId demandeId,@RequestParam("userId") Long userId,@RequestParam("clubId") Long clubId,  @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "6") int size,ModelMap modelMap) throws ParseException{
       Club club=clubService.getClub(clubId);
       User user=userService.getById(userId);
        Role role = roleRepository.findByName("ROLE_MEMBRE");
        user.getRoles().add(role);
        user.getClubs().add(club);
        club.getUsers().add(user);
        demandeId.setClub(club);
        demandeId.setUser(user);
        DemandeMembre demandeMembre=demandeMembreService.getDemandeMembre(demandeId);
        demandeMembre.setStatus("accepté");
        userService.save(user);
        demandeMembreService.saveDemandeMembre(demandeMembre);
        return "redirect:/ListDemandeMembre";
    }

    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/refuserDemandeMembre")
    public String refuserDemandeMembre(@ModelAttribute("demandeId")DemandeMembreId demandeId,@RequestParam("userId") Long userId,@RequestParam("clubId") Long clubId,  @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "6") int size,ModelMap modelMap) throws ParseException{
        Club club=clubService.getClub(clubId);
        User user=userService.getById(userId);
        DemandeMembre demandeMembre=demandeMembreService.getDemandeMembre(demandeId);
        demandeMembre.setStatus("refusé");
        userService.save(user);
        demandeMembreService.saveDemandeMembre(demandeMembre);
        return "redirect:/ListDemandeMembre";
    }
    @PreAuthorize(" hasRole('ADMIN')")
    @RequestMapping("/ListDemandeClub")
    public String listeDemandeClub(
            ModelMap modelMap,@RequestParam(name = "nom", defaultValue = "") String nom,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size)

    {
        Page<DemandeClub> ac = demandeClubService.getAllDemandeClubParPage(nom,page, size);

        modelMap.addAttribute("demandeClub", ac);
        modelMap.addAttribute("pages", new int[ac.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "listeDemandeClub";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/accepterDemandeClub")
    public String accepterDemandeMembre(@RequestParam("demandeId")Long demandeId,  @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "6") int size,ModelMap modelMap) throws ParseException{


        DemandeClub demandeClub=demandeClubService.getDemandeClub(demandeId);

        Club club =new Club(demandeClub.getNomClub(),demandeClub.getNbMembre(),demandeClub.getDateCreation(),null,demandeClub.getUser(),null);
        User user=demandeClub.getUser();
        Role role = roleRepository.findByName("ROLE_PRESIDENT");
        user.getRoles().add(role);
        demandeClub.setStatus("accepté");
        userService.save(user);
        clubService.save(club);
        demandeClubService.saveDemandeClub(demandeClub);
        return "redirect:/ListDemandeClub";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/refuserDemandeClub")
    public String refuserDemandeClub(@ModelAttribute("demandeId")Long demandeId,  @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "6") int size,ModelMap modelMap) throws ParseException{

        DemandeClub demandeClub=demandeClubService.getDemandeClub(demandeId);
        demandeClub.setStatus("refusé");
        demandeClubService.saveDemandeClub(demandeClub);
        return "redirect:/ListDemandeClub";
    }
    @PreAuthorize(" hasRole('PRESIDENT')")
    @RequestMapping("/ListDemandeParticipation")
    public String listeDemandeParticipation( @AuthenticationPrincipal UserDetails userDetails,
                                      ModelMap modelMap,@RequestParam(name = "nom", defaultValue = "") String nom,
                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "6") int size)

    {
        Page<DemandeActivite> ac = demandeActiviteService.getAllDemandeActiviteParPage(nom,page, size);
        String username = userDetails.getUsername();
        User user = userService.findUsername(username);
        modelMap.addAttribute("demandeParticipation", ac);
        modelMap.addAttribute("users", user);
        List<Club> clubs = clubService.getAllClubs();
        modelMap.addAttribute("clubs", clubs);
        modelMap.addAttribute("pages", new int[ac.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "listeDemandeParticipation";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/accepterDemandeParticipation")
    public String accepterDemandeParticipation(@ModelAttribute("demandeId")DemandeActiviteId demandeId,@RequestParam("userId") Long userId,@RequestParam("activiteId") Long activiteId,  @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "6") int size,ModelMap modelMap) throws ParseException{
        Activite activite=activiteService.getActivite(activiteId);
        User user=userService.getById(userId);
        user.getActivite().add(activite);
        activite.getUser().add(user);
        demandeId.setActivite(activite);
        demandeId.setUser(user);
        DemandeActivite demandeActivite=demandeActiviteService.getDemandeActivite(demandeId);
        demandeActivite.setStatus("accepté");
        userService.save(user);
        demandeActiviteService.saveDemandeActivite(demandeActivite);
        return "redirect:/ListDemandeParticipation";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/refuserDemandeParticipation")
    public String refuserDemandeParticipation(@ModelAttribute("demandeId")DemandeActiviteId demandeId,@RequestParam("userId") Long userId,@RequestParam("activiteId") Long activiteId,  @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "6") int size,ModelMap modelMap) throws ParseException{
        Activite activite=activiteService.getActivite(activiteId);
        User user=userService.getById(userId);
        demandeId.setActivite(activite);
        demandeId.setUser(user);
        DemandeActivite demandeActivite=demandeActiviteService.getDemandeActivite(demandeId);
        demandeActivite.setStatus("refusé");
        demandeActiviteService.saveDemandeActivite(demandeActivite);
        return "redirect:/ListDemandeParticipation";
    }


    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/ListDemandeActivite")
    public String listeDemandeActivite( @AuthenticationPrincipal UserDetails userDetails,
            ModelMap modelMap,@RequestParam(name = "nom", defaultValue = "") String nom,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size)

    { String username = userDetails.getUsername();
        User user = userService.findUsername(username);
        modelMap.addAttribute("users", user);
        Page<DemandeAjoutActivite> ac = demandeAjoutActiviteService.getAllDemandeAjoutActiviteParPage(nom,page, size);
        List<Club> clubs = clubService.getAllClubs();
        modelMap.addAttribute("clubs", clubs);
        modelMap.addAttribute("demandeActivite", ac);
        modelMap.addAttribute("pages", new int[ac.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "listeDemandeActivite";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/accepterDemandeActivite")
    public String accepterDemandeActivite(@RequestParam("demandeId")Long demandeId,  @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "6") int size,ModelMap modelMap) throws ParseException{


        DemandeAjoutActivite demandeAjoutActivite=demandeAjoutActiviteService.getDemandeAjoutActivite(demandeId);

        Activite activite =new Activite(demandeAjoutActivite.getNomActivite(),demandeAjoutActivite.getLieu(),demandeAjoutActivite.getDateActivite(),demandeAjoutActivite.getPrixActivite(),demandeAjoutActivite.getSujet(),demandeAjoutActivite.getClub(),null);
        demandeAjoutActivite.setStatus("accepté");
        activiteService.save(activite);
        demandeAjoutActiviteService.saveDemandeAjoutActivite(demandeAjoutActivite);
        return "redirect:/ListDemandeActivite";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/refuserDemandeActivite")
    public String refuserDemandeActivite(@ModelAttribute("demandeId")Long demandeId,  @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "6") int size,ModelMap modelMap) throws ParseException{

        DemandeAjoutActivite demandeAjoutActivite=demandeAjoutActiviteService.getDemandeAjoutActivite(demandeId);
        demandeAjoutActivite.setStatus("refusé");
        demandeAjoutActiviteService.saveDemandeAjoutActivite(demandeAjoutActivite);
        return "redirect:/ListDemandeActivite";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/showCreate")
    public String showCreate(@AuthenticationPrincipal UserDetails userDetails,ModelMap modelMap)
    {String username = userDetails.getUsername();
        User user = userService.findUsername(username); modelMap.addAttribute("users", user);
        List<Club> clubs = clubService.getAllClubs();
        modelMap.addAttribute("clubs", clubs);
        modelMap.addAttribute("membre",new User());
        return "createMembre";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/saveMembre")
    public String saveMembre(@ModelAttribute("membre") User user,
                             @RequestParam("clubs.clubId") Long clubId,
                             ModelMap modelMap) throws ParseException {
        Club club = clubService.getClub(clubId);
        club.getUsers().add(user);
        Role role = roleRepository.findByName("ROLE_MEMBRE");
        user.getRoles().add(role);
        user.getClubs().add(club);
        userService.save(user);
        clubService.save(club);
        String msg = "Membre ajouté";
        modelMap.addAttribute("msg", msg);

        return "createMembre";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/listeMembre")
    public String listeMembre(@AuthenticationPrincipal UserDetails userDetails,  @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "6") int size,
                              ModelMap modelMap) {
        String username = userDetails.getUsername();
        User user = userService.findUsername(username);
        modelMap.addAttribute("user", user);
        List<Club> club = clubService.getAllClubs();
        modelMap.addAttribute("club", club);
        Page<Club> clubs = clubService.getClubsByPresident(user,page,size);
        modelMap.addAttribute("clubs", clubs);
        modelMap.addAttribute("pages", new int[clubs.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "listeMembre";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/supprimerMembre")
    public String supprimerMembre(@RequestParam("id") Long id,@RequestParam("idClub") Long idClub, ModelMap
            modelMap,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "5") int size) {

        User user=userService.getById(id);
        Role role = roleRepository.findByName("ROLE_Membre");
        user.getRoles().remove(role);
        Club club=clubService.getClub(idClub);
        user.getClubs().remove(club);
        club.getUsers().remove(user);
        userServiceImp.saveUser(user);
        clubService.save(club);
        Page<User> formats = userServiceImp.getAllUserParPage(page, size);
        modelMap.addAttribute("membres", formats);
        modelMap.addAttribute("pages", new int[formats.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "redirect:/listeMembre";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/modifierMembre")
    public String editerMembre(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("id") Long id,ModelMap modelMap)
    { String username = userDetails.getUsername();
        User user = userService.findUsername(username); modelMap.addAttribute("users", user);
        List<Club> clubs = clubService.getAllClubs();
        modelMap.addAttribute("clubs", clubs);
        User f= userService.getById(id);
        modelMap.addAttribute("membre", f);
        return "editerMembre";
    }
    @RequestMapping("/modifierUser")
    public String editerUser(@AuthenticationPrincipal UserDetails userDetails,ModelMap modelMap)
    { String username = userDetails.getUsername();
        User user = userService.findUsername(username); modelMap.addAttribute("users", user);
        User f= userService.getById(user.getUserId());
        modelMap.addAttribute("membre", f);
        return "editerUser";
    }
    @PostMapping("/updateUser")
    public String updateMembre(@ModelAttribute("membre") User user,
                               ModelMap modelMap )throws ParseException
    {
        userServiceImp.updateUser(user);
        return "editerUser";
    }



    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/showActivite")
    public String showActivite(@AuthenticationPrincipal UserDetails userDetails,@ModelAttribute("activite") Activite activite, ModelMap modelMap)
    {String username = userDetails.getUsername();
        User user = userService.findUsername(username); modelMap.addAttribute("users", user);
        List<Club> clubs = clubService.getAllClubs();
        modelMap.addAttribute("clubs", clubs);
        modelMap.addAttribute("activite",activite);
        return "createActivite";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/saveActivite")
    public String saveActivite(@ModelAttribute("activite")Activite activite,@RequestParam("clubs.clubId") Long clubId,ModelMap modelMap) throws ParseException {
    Club club=clubService.getClub(clubId);
    activite.setClub(club);
    activiteService.save(activite);

        String msg = "activite ajouté";
        modelMap.addAttribute("msg", msg);

        return "createActivite";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/listeActiviteClub")
    public String listeActivite(@AuthenticationPrincipal UserDetails userDetails ,@RequestParam(name = "nom", defaultValue = "") String nom,@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "6") int size, ModelMap modelMap) {
        String username = userDetails.getUsername();
        User user = userService.findUsername(username);
        modelMap.addAttribute("user", user);
        List<Club> club = clubService.getAllClubs();
        modelMap.addAttribute("clubs", club);
        Page<Activite> acts = activiteService.getAllActiviteParPage(nom,page,size);
        modelMap.addAttribute("activite", acts);
        modelMap.addAttribute("pages", new int[acts.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "listeActiviteClub";
    }
    @PreAuthorize("hasRole('ROLE_PRESIDENT')")
    @RequestMapping("/rechercherActivite")
    public String rechercherActivitye(@RequestParam("nom") String nom, ModelMap modelMap,
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "6") int size) {
        Page<Activite> prods = activiteService.findByNomActivite(nom, PageRequest.of(page, size));
        modelMap.addAttribute("activite", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        modelMap.addAttribute("nom", nom);
        return "listeActiviteClub";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/supprimerActivite")
    public String supprimerActivite(@RequestParam("id") Long id,@RequestParam(name = "nom", defaultValue = "") String nom, ModelMap
            modelMap, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size) {
try
        {  Activite activite=activiteService.getActivite(id);
            Set<User> participants = activite.getUser();
            for (User participant : participants) {
                participant.getActivite().remove(activite);
                userService.save(participant);
            }
            activiteService.deleteActiviteById(id);}
         catch (DataIntegrityViolationException e) {
        String errorMessage = "Impossible de supprimer l'activité en raison de contraintes d'intégrité.";
        modelMap.addAttribute("error", errorMessage);
        return "error-page-activite";
    }
        Page<Activite> formats = activiteService.getAllActiviteParPage(nom,page, size);
        modelMap.addAttribute("activite", formats);
        modelMap.addAttribute("pages", new int[formats.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "redirect:/listeActiviteClub";
    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @RequestMapping("/modifierActivite")
    public String editerActivite(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("id") Long id,ModelMap modelMap)
    {    Activite a= activiteService.getActivite(id);
        modelMap.addAttribute("activite", a);
        return "editerActivite";

    }
    @PreAuthorize("hasRole('PRESIDENT')")
    @PostMapping("/updateActivite")
    public String updateActivite(@ModelAttribute("activite") Activite activite,@RequestParam(name = "nom", defaultValue = "") String nom,@RequestParam(name = "page", defaultValue = "1") int page,
                                 @RequestParam(name = "size", defaultValue = "5") int size,
                                 ModelMap modelMap )throws ParseException
    {
        activiteService.updateActivite(activite);
        Page<Activite> formats = activiteService.getAllActiviteParPage(nom,page, size);
        modelMap.addAttribute("activite", formats);
        modelMap.addAttribute("pages", new int[formats.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "redirect:/listeActiviteClub";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/modifierClub")
    public String editerClub(@RequestParam("id") Long id,ModelMap modelMap)
    {    Club c= clubService.getClub(id);
        modelMap.addAttribute("club", c);
        return "editerClub";

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/updateClub")
    public String updateClub(@ModelAttribute("club") Club updatedClub,@RequestParam(name = "nom", defaultValue = "") String nom,@RequestParam(name = "page", defaultValue = "1") int page,
                                 @RequestParam(name = "size", defaultValue = "5") int size,
                                 ModelMap modelMap )throws ParseException
     {
        Club club = clubService.getClub(updatedClub.getClubId());
        club.setNomClub(updatedClub.getNomClub());
        club.setNbMembre(updatedClub.getNbMembre());
        club.setDateCreation(updatedClub.getDateCreation());
        clubService.updateClub(club);
        Page<Club> formats = clubService.getAllClubsParPage(nom,page, size);
        modelMap.addAttribute("clubs", formats);
        modelMap.addAttribute("pages", new int[formats.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "redirect:/ListeClub";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/supprimerClub")
    public String supprimerClub(@RequestParam("id") Long id, ModelMap modelMap,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size) {
try{
        Club club = clubService.getClub(id);
        User president = club.getPresident();
        Role rolePresident = roleRepository.findByName("ROLE_PRESIDENT");
        Role roleMembre = roleRepository.findByName("ROLE_MEMBRE");

        int presidentCount = president.getClubs().size();
        if (presidentCount <= 1) {
            president.getRoles().remove(rolePresident);
        }
        userService.save(president);

        Set<User> membres = club.getUsers();
        int membreCount = membres.size();
        for (User membre : membres) {
            if (membreCount <= 1) {
                membre.getRoles().remove(roleMembre);
            }
            membre.getClubs().remove(club);
            userService.save(membre);
        }

        List<Activite> activites = club.getActivite();
        for (Activite activite : activites) {
            Set<User> participants = activite.getUser();
            for (User participant : participants) {
                participant.getActivite().remove(activite);
                userService.save(participant);
            }
            activiteService.deleteActiviteById(activite.getActiviteId());
        }

        clubService.deleteClubById(id);}
         catch (DataIntegrityViolationException e) {
            String errorMessage = "Impossible de supprimer le club en raison de contraintes d'intégrité.";
            modelMap.addAttribute("error", errorMessage);
            return "error-page";
        }
        Page<User> formats = userServiceImp.getAllUserParPage(page, size);
        modelMap.addAttribute("clubs", formats);
        modelMap.addAttribute("pages", new int[formats.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);

        return "redirect:/ListeClub";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/showClub")
    public String showClub(@ModelAttribute("club") Club club,@ModelAttribute("president") User user,ModelMap modelMap)
    {
        modelMap.addAttribute("club",club);
        modelMap.addAttribute("president",user);
        return "ajouterClub";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/saveClub")
    public String saveClub(@ModelAttribute("club") Club club,@ModelAttribute("president") User user,
                             ModelMap modelMap) throws ParseException {

        club.setPresident(user);
        Role role = roleRepository.findByName("ROLE_PRESIDENT");
        user.getRoles().add(role);
        userService.save(user);
        clubService.save(club);
        String msg = "club ajouté";
        modelMap.addAttribute("msg", msg);

        return "ajouterClub";
    }
    @PreAuthorize("hasRole('VISITEUR') || hasRole('ADMIN')")
    @RequestMapping("/rechercherClub")
    public String rechercherClub(@RequestParam("nom") String nom, ModelMap modelMap,
                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "6") int size) {
        Page<Club> prods = clubService.findByNomClub(nom, PageRequest.of(page, size));
        modelMap.addAttribute("clubs", prods);
        modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        modelMap.addAttribute("nom", nom);
        return "listeClub";
    }
}
