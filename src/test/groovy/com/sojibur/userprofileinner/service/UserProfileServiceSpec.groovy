package com.sojibur.userprofileinner.service

import com.sojibur.userprofileinner.exception.InternalServerException
import com.sojibur.userprofileinner.exception.UserNotFoundException
import com.sojibur.userprofileinner.model.Address
import com.sojibur.userprofileinner.model.User
import com.sojibur.userprofileinner.model.Users
import com.sojibur.userprofileinner.repository.UserProfileRepository
import com.sojibur.userprofileinner.service.impl.UserProfileServiceImpl
import spock.lang.Specification

class UserProfileServiceSpec extends Specification{
    UserProfileService mockUserProfileService
    UserProfileRepository mockUserProfileRepository

    def setup(){
        mockUserProfileRepository = Mock()
        mockUserProfileService = new UserProfileServiceImpl(mockUserProfileRepository)
    }

    def "should return a list of user if the call to the repository findAll() method is successful"(){
        given:
        def mockUser = [id:"0000",
                        firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]] as User

        when:
        Users actualUsers = mockUserProfileService.getAllUser()

        then:
        1 * mockUserProfileRepository.findAll() >> [mockUser]
        actualUsers.users[0] == mockUser
    }

    def "should throw InternalServerException if the call to the repository findAll() method is unsuccessful"(){
        when:
        mockUserProfileService.getAllUser()

        then:
        1 * mockUserProfileRepository.findAll() >> {throw new Exception()}
        thrown InternalServerException
    }

    def "should return created user if the call to the repository save() method is successful"(){
        given:
        def mockUser = [id:"0000",
                        firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]] as User

        when:
        User actualUser = mockUserProfileService.createUser(mockUser)

        then:
        1 * mockUserProfileRepository.save(mockUser) >> mockUser
        actualUser == mockUser
    }

    def "should throw InternalServerException if the call to the repository save() method is unsuccessful"(){
        given:
        def mockUser = [id:"0000",
                        firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]
                        ] as User

        when:
        mockUserProfileService.createUser(mockUser)

        then:
        1 * mockUserProfileRepository.save(mockUser) >> {throw new Exception()}
        thrown InternalServerException
    }

    def "should return a user if the call to the repository findById() method is successful"(){
        given:
        def mockUserId = "0000"
        def mockUser = [id:mockUserId,
                        firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]] as User

        when:
        User actualUser = mockUserProfileService.findUserById(mockUserId)

        then:
        1 * mockUserProfileRepository.findById(mockUserId) >> Optional.of(mockUser)
        actualUser == mockUser
    }

    def "should throw UserNotFoundException if the call to the repository findById() method is unsuccessful"(){
        given:
        def mockUserId = "0000"

        when:
        mockUserProfileService.findUserById(mockUserId)

        then:
        1 * mockUserProfileRepository.findById(mockUserId) >> Optional.empty()
        thrown UserNotFoundException
    }

    def "should call repository delete method if the user is found"(){
        given:
        def mockUserId = "0000"
        def mockUser = [id:mockUserId,
                        firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]] as User

        when:
        mockUserProfileService.deleteUserById(mockUserId)

        then:
        1 * mockUserProfileRepository.findById(mockUserId) >> Optional.of(mockUser)
        1 * mockUserProfileRepository.delete(mockUser)
    }

    def "should throw UserNotFoundException if item to be deleted not found"(){
        given:
        def mockUserId = "0000"

        when:
        mockUserProfileService.deleteUserById(mockUserId)

        then:
        1 * mockUserProfileRepository.findById(mockUserId) >> Optional.empty()
        thrown UserNotFoundException
    }

    def "should return updated user if the update is successful"(){
        given:
        def mockUserId = "0000"
        def mockUser = [id:mockUserId,
                        firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]] as User
        def mockSavedUser = [id:mockUserId,
                             firstName: "Jane",
                             lastName: "Doe",
                             email:"Jane.Doe@email.com",
                             address:[street:"123 main st", city:"Utopia"] as Address,
                             hobbies:["hobby1"],
                             regions:["us-east-1"]] as User

        when:
        User actualUser = mockUserProfileService.updateUser(mockUserId, mockUser)

        then:
        1 * mockUserProfileRepository.findById(mockUserId) >> Optional.of(mockSavedUser)
        1 * mockUserProfileRepository.save(mockSavedUser) >> mockUser
        actualUser == mockUser
    }

    def "should throw UserNotFoundException if item to be updated not found"(){
        given:
        def mockUserId = "0000"

        def mockUser = [id:mockUserId,
                        firstName: "John",
                        lastName: "Doe",
                        email:"John.Doe@email.com",
                        address:[street:"123 main st", city:"Utopia"] as Address,
                        hobbies:["hobby1"],
                        regions:["us-east-1"]] as User

        when:
        mockUserProfileService.updateUser(mockUserId, mockUser)

        then:
        1 * mockUserProfileRepository.findById(mockUserId) >> Optional.empty()
        thrown UserNotFoundException
    }
}
