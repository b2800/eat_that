class Recipe < ActiveRecord::Base
	has_many :list
	has_many :ingredients, :through => :list
end
