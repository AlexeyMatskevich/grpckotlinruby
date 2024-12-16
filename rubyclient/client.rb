require 'bundler/setup'
Bundler.require(:default)

require_relative 'cross_orm_services_pb'

stub = Org::Example::Crossorm::CrossORM::Stub.new('localhost:8980', :this_channel_is_insecure)

get_user = stub.get_user(Org::Example::Crossorm::UserID.new(id: 1))

puts "Get user #{get_user}"

create_user = stub.create_user(Org::Example::Crossorm::NewUser.new(name: "new user", age: 18))

puts "Create user #{create_user}"
