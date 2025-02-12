# Generated by the protocol buffer compiler.  DO NOT EDIT!
# Source: cross_orm.proto for package 'org.example.crossorm'

require 'grpc'
require_relative 'cross_orm_pb'

module Org
  module Example
    module Crossorm
      module CrossORM
        class Service

          include ::GRPC::GenericService

          self.marshal_class_method = :encode
          self.unmarshal_class_method = :decode
          self.service_name = 'org.example.crossorm.CrossORM'

          rpc :GetUser, ::Org::Example::Crossorm::UserID, ::Org::Example::Crossorm::User
          rpc :CreateUser, ::Org::Example::Crossorm::NewUser, ::Org::Example::Crossorm::User
        end

        Stub = Service.rpc_stub_class
      end
    end
  end
end
