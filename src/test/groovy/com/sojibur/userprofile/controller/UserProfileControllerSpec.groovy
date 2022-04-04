package com.sojibur.userprofile.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sojibur.userprofile.exception.ApiError
import com.sojibur.userprofile.exception.GlobalExceptionHandler
import com.sojibur.userprofile.exception.InternalServerException
import com.sojibur.userprofile.exception.UserNotFoundException
import com.sojibur.userprofile.model.Address
import com.sojibur.userprofile.model.User
import com.sojibur.userprofile.model.Users
import com.sojibur.userprofile.service.UserProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserProfileControllerSpec extends Specification{
    ObjectMapper objectMapper
    MockMvc mockMvc
    UserProfileService mockUserProfileService
    def mockUserProfileController

    def setup() {
        objectMapper = new ObjectMapper()
        mockUserProfileService = Mock()
        mockUserProfileController = new UserProfileController(mockUserProfileService)
        mockMvc = MockMvcBuilders.standaloneSetup(mockUserProfileController)
                .setControllerAdvice(new GlobalExceptionHandler()).build()
    }

    def "should return a list of user when a GET call to /api/users is successful"() {
        given:
        def mockUser = [firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]] as User
        def mockUsers = new Users()
        mockUsers.setUsers([mockUser])

        when:
        def response = mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andReturn()
        def content = response.getResponse().getContentAsString()
        Users actualUsers = objectMapper.readValue(content, Users.class)

        then:
        1 * mockUserProfileService.getAllUser() >> mockUsers
        actualUsers == mockUsers
    }

    def "should throw internal server error GET call to /api/users fails"() {
        when:
        def response = mockMvc.perform(get("/api/users"))
                .andExpect(status().isInternalServerError())
                .andReturn()
        def content = response.getResponse().getContentAsString()
        ApiError apiError = objectMapper.readValue(content, ApiError.class)

        then:
        1 * mockUserProfileService.getAllUser() >> { throw new InternalServerException("internal-server-error")}
        apiError.status == HttpStatus.INTERNAL_SERVER_ERROR
        apiError.errors[0].message == "internal-server-error"
    }

    def "should return user when a POST call to /api/users is successful"() {
        given:
        def mockUser = [id:"1234",
                        firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]] as User

        when:
        def response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isCreated())
                .andReturn()
        def content = response.getResponse().getContentAsString()
        User actualUser = objectMapper.readValue(content, User.class)

        then:
        1 * mockUserProfileService.createUser(mockUser) >> mockUser
        actualUser == mockUser
    }

    def "should throw InternalServerException if a POST call to /api/users fails"() {
        given:
        def mockUser = [id:"1234",
                        firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]] as User

        when:
        def response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isInternalServerError())
                .andReturn()
        def content = response.getResponse().getContentAsString()
        ApiError apiError = objectMapper.readValue(content, ApiError.class)

        then:
        1 * mockUserProfileService.createUser(mockUser) >> {throw new InternalServerException("internal-server-error")}
        apiError.status == HttpStatus.INTERNAL_SERVER_ERROR
        apiError.errors[0].message == "internal-server-error"
    }

    def "should return a user when a GET call to /api/users with an id is successful"() {
        given:
        def mockUserId ="0000"
        def mockUser = [id:"1234",
                        firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]] as User

        when:
        def response = mockMvc.perform(get("/api/users/"+mockUserId))
                .andExpect(status().isOk())
                .andReturn()
        def content = response.getResponse().getContentAsString()
        User actualUser = objectMapper.readValue(content, User.class)

        then:
        1 * mockUserProfileService.findUserById(mockUserId) >> mockUser
        actualUser == mockUser
    }

    def "should throw UserNotFoundException when a GET call to /api/users with an id fails"() {
        given:
        def mockUserId ="0000"

        when:
        def response = mockMvc.perform(get("/api/users/"+mockUserId))
                .andExpect(status().isNotFound())
                .andReturn()
        def content = response.getResponse().getContentAsString()
        ApiError apiError = objectMapper.readValue(content, ApiError.class)

        then:
        1 * mockUserProfileService.findUserById(mockUserId) >> {throw new UserNotFoundException("user-not-found")}
        apiError.status == HttpStatus.NOT_FOUND
        apiError.errors[0].message == "user-not-found"
    }

    def "should return 200 Ok if a DELETE call to /api/users with an id is successful"() {
        given:
        def mockUserId = "0000"

        when:
        def response = mockMvc.perform(delete("/api/users/" + mockUserId))
                .andExpect(status().isOk())
                .andReturn()
        def content = response.getResponse().getContentAsString()

        then:
        1 * mockUserProfileService.deleteUserById(mockUserId)
        content == "RECORD_SUCCESSFULLY_DELETED"
    }

    def "should throw UserNotFoundException when a DELETE call to /api/users with an id fails"() {
        given:
        def mockUserId ="0000"

        when:
        def response = mockMvc.perform(delete("/api/users/"+mockUserId))
                .andExpect(status().isNotFound())
                .andReturn()
        def content = response.getResponse().getContentAsString()
        ApiError apiError = objectMapper.readValue(content, ApiError.class)

        then:
        1 * mockUserProfileService.deleteUserById(mockUserId) >> {throw new UserNotFoundException("user-not-found")}
        apiError.status == HttpStatus.NOT_FOUND
        apiError.errors[0].message == "user-not-found"
    }

    def "should return updated user when a PUT call to /api/users with an id is successful"() {
        given:
        def mockUserId = "0000"
        def mockUser = [id:"1234",
                        firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]] as User

        when:
        def response = mockMvc.perform(put("/api/users/"+mockUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andReturn()
        def content = response.getResponse().getContentAsString()
        User actualUser = objectMapper.readValue(content, User.class)

        then:
        1 * mockUserProfileService.updateUser(mockUserId, mockUser) >> mockUser
        actualUser == mockUser
    }

    def "should throw UserNotFoundException when a PUT call to /api/users with an id fails"() {
        given:
        def mockUserId = "0000"
        def mockUser = [id:"1234",
                        firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]] as User

        when:
        def response = mockMvc.perform(put("/api/users/"+mockUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isNotFound())
                .andReturn()
        def content = response.getResponse().getContentAsString()
        ApiError apiError = objectMapper.readValue(content, ApiError.class)

        then:
        1 * mockUserProfileService.updateUser(mockUserId, mockUser) >> {throw new UserNotFoundException("user-not-found")}
        apiError.status == HttpStatus.NOT_FOUND
        apiError.errors[0].message == "user-not-found"
    }
}
